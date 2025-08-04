#!/usr/bin/env bash
set -euo pipefail

die_json_err() {
    echo "Response invalid JSON:"
    echo "$1"
    exit 1
}

check_api_error() {
    local resp="$1"
    if [ "$(echo "$resp" | jq -r 'has("error")')" = "true" ]; then
        echo "Error $(echo "$resp" | jq -r '.error.message // .error // .')"
        exit 1
    fi
}

chunk_stdin_by_chars() {
    local maxlen="$1"
    local outdir="$2"
    mkdir -p "$outdir"
    awk -v limit="$maxlen" -v dir="$outdir" '
      BEGIN{ RS=""; ORS=""; n=0; acc=""; }
      {
        para=$0; gsub(/\n+/, " ", para)
        if (length(acc)+length(para)+1 > limit && length(acc) > 0) {
          n++; fname=sprintf("%s/chunk_%04d.txt", dir, n)
          print acc > fname; close(fname); acc=""
        }
        if (length(para) > limit) {
          p=para
          while (length(p) > limit) {
            n++; fname=sprintf("%s/chunk_%04d.txt", dir, n)
            print substr(p,1,limit) > fname; close(fname)
            p=substr(p,limit+1)
          }
          acc=(length(p)?p:"")
        } else {
          acc=(acc==""?para:acc " " para)
        }
      }
      END{
        if (length(acc) > 0) {
          n++; fname=sprintf("%s/chunk_%04d.txt", dir, n)
          print acc > fname; close(fname)
        }
      }
    '
}

if [ $# -lt 2 ]; then
    echo "Usage $0 {input-file} export_folder"
    exit 1
fi

input="$1"
target="$2"

if [ -z "${OPENAI_API_KEY:-}" ]; then
    echo "Please set your OPENAI_API_KEY environment variable."
    exit 1
fi

target="$(pwd "$input")/exports/$2/$(basename "$input")"
mkdir -p "$(pwd)/exports/$2" && echo "Processing $input"

SYSTEM_PROMPT="$(
    cat <<'EOF'
You are an expert narration editor preparing plain UTF-8 text to be read aloud as an audiobook. The content is of technical nature and meant to be consumed by programmers and developers. NEVER omit, remove or abbreviate anything from the files when generating the content, we are NOT creating a summary of the document, we are creating a document ready to be fed into a text to speech agent, but it must be representative and reflective of the original file to the upmoset detail.

Primary goals:
- Preserve the author’s meaning while optimizing for natural, human-sounding delivery.
- Produce text that flows like professional narration with clear pacing, emphasis, and transitions.
- Final output must be plain text (no Markdown artifacts, backticks, or SSML).

Voice & pacing:
- Warm, clear, and conversational—not robotic. Prefer short-to-medium sentences.
- Use **blank lines between paragraphs** to create natural long pauses.
- Use punctuation for micro-pauses:
  - commas for light pauses,
  - em dashes (—) for emphasis or asides,
  - ellipses (…) sparingly to signal a reflective beat.
- Do not insert stage directions like “[pause]” or “(beat)”.

Structure & flow:
- Rewrite lists into smooth prose. Avoid bullets and nested structure in the final text.
- Where the source has headings, keep the heading text but read it as a clean lead-in line, followed by a paragraph break.
- Add short connective phrases when jumping topics (e.g., “Next,” “Now,” “In contrast,”) only if it improves flow.

Code, commands, and config:
- Do not read raw syntax or punctuation-heavy content verbatim.
- For each code block, replace with a 1–3 sentence, plain-English summary of what it does and why it matters. Mention key function/class names, but avoid symbol-level detail. Try to explain what the essense of it is.
  - Example: “This block defines a function called add that takes two numbers and returns their sum.”
- For CLI or config snippets, summarize their purpose and outcome (e.g., “This installs the dependencies {name},” “This command fetches the page and prints the status from {website}”).

Clarity & listenability:
- Expand dense sentences slightly for clarity; split run-ons.
- Read numbers in a listener-friendly way:
  - Years as “twenty twenty-five,” large numbers grouped sensibly (“one thousand two hundred”).
  - Units spelled out the firs
EOF
)"

summary="${target}-tts.txt"

if [[ -f "$summary" ]]; then
    echo "Text to speech already exists for $input."
else
    echo "Converting $input to text to speech ..."
    tmp_sum="$(mktemp -d)"
    in_chunks="$tmp_sum/in_chunks"
    out_chunks="$tmp_sum/out_chunks"
    mkdir -p "$in_chunks" "$out_chunks"

    summary_maxlen=9000
    chunk_stdin_by_chars "$summary_maxlen" "$in_chunks" <"$input.md"
    mapfile -t in_files < <(printf '%s\n' "$in_chunks"/chunk_*.txt | sort)
    if [ ${#in_files[@]} -eq 0 ]; then
        echo "No input chunks produced; cannot summarize."
        exit 1
    fi
    echo "Created ${#in_files[@]} input chunk(s)."

    idx=0
    for f in "${in_files[@]}"; do
        idx=$((idx + 1))
        echo "[$idx/${#in_files[@]}] Converting $(basename "$f") ..."
        req_json="$(jq -n \
            --arg sys "$SYSTEM_PROMPT" \
            --rawfile content "$f" \
            '{
         model: "gpt-4.1",
         messages: [
           {role:"system", content:$sys},
           {role:"user",   content:$content}
         ],
         temperature: 0
       }')"

        resp="$(curl -sS https://api.openai.com/v1/chat/completions \
            -H "Content-Type: application/json" \
            -H "Authorization: Bearer $OPENAI_API_KEY" \
            --data "$req_json")"

        echo "$resp" | jq . >/dev/null 2>&1 || die_json_err "$resp"
        check_api_error "$resp"

        chunk_summary="$(echo "$resp" | jq -r '.choices[0].message.content')"
        echo "$chunk_summary" >"$out_chunks/summary_${idx}.txt"
    done

    touch "$summary"
    for i in $(seq 1 "${#in_files[@]}"); do
        cat "$out_chunks/summary_${i}.txt" >>"$summary"
        printf '\n\n' >>"$summary"
    done

    rm -rf "$tmp_sum"
    echo "Saved summary to $summary"
fi

outfile="${target}.mp3"

if [[ -f "$outfile" ]]; then
    echo "Audio file alrady exists for $input."
else
    echo "Generating TTS from $summary..."

    maxlen=3900
    tmpdir="$(mktemp -d)"
    chunks_dir="$tmpdir/chunks"
    mkdir -p "$chunks_dir"

    chunk_stdin_by_chars "$maxlen" "$chunks_dir" <"$summary"
    mapfile -t chunk_files < <(printf '%s\n' "$chunks_dir"/chunk_*.txt | sort)
    if [ ${#chunk_files[@]} -eq 0 ]; then
        echo "No chunks produced for TTS."
        rm -rf "$tmpdir"
        exit 1
    fi

    parts_dir="$tmpdir/parts"
    mkdir -p "$parts_dir"
    part_list="$tmpdir/parts.txt"
    : >"$part_list"
    index=0
    for chunk in "${chunk_files[@]}"; do
        index=$((index + 1))
        echo "[${index}/${#chunk_files[@]}] Synthesizing $(basename "$chunk")..."
        txt="$(cat "$chunk")"

        request="$(jq -n \
            --arg model "tts-1" \
            --arg voice "alloy" \
            --arg format "mp3" \
            --arg input "$txt" \
            '{model:$model, voice:$voice, input:$input, format:$format}')"

        audio="$(mktemp "$parts_dir/part_${index}_XXXX.mp3")"
        headers="$(mktemp "$tmpdir/hdr_${index}_XXXX")"

        curl -sS -D "$headers" https://api.openai.com/v1/audio/speech \
            -H "Authorization: Bearer $OPENAI_API_KEY" \
            -H "Content-Type: application/json" \
            --data "$request" \
            -o "$audio"

        ctype="$(tr -d '\r' <"$headers" | awk -F': ' 'tolower($1)=="content-type"{print tolower($2)}' | tail -n1)"
        if [[ "$ctype" != audio/* ]]; then
            echo "Chunk $index failed (non-audio response)."
            echo "HTTP status: $(head -n1 "$headers")"
            echo "Content-Type: ${ctype:-unknown}"
            echo "Body:"
            echo
            sed 's/^/  /' "$audio"
            rm -rf "$tmpdir"
            exit 1
        fi

        echo "file '$audio'" >>"$part_list"
    done

    echo "Concatenating ${#chunk_files[@]} chunks..."
    if ffmpeg -hide_banner -loglevel error -f concat -safe 0 -i "$part_list" -c copy "$target.mp3"; then
        echo "Saving TTS result to $outfile"
    else
        echo "Stream copy failed; re-encoding it to guarantee seamless audio..."
        ffmpeg -y -hide_banner -loglevel error -f concat -safe 0 -i "$part_list" -c:a libmp3lame -b:a 192k "$target.mp3"
        echo "Saving (re-encoded) to $outfile"
    fi
    rm -rf "$tmpdir"
fi
