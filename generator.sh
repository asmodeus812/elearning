#!/bin/bash

if [ $# -lt 1 ]; then
    echo "Usage $0 {input_file}"
    exit 1
fi

source=$1
cwd="$(pwd)"
cwd="${cwd%/}"

input=$(basename "$source")
relative="${source#"$cwd"/}"
target=$(dirname "$cwd/exports/$relative")
mkdir -p "$target" && echo "Processing $input"

source="$cwd/$source"
target="$target/$input"

if [[ -f "$target.pdf" ]]; then
    echo "Resources already generated for $input"
else
    echo "Generating resources for target $input..."
    pandoc -t html5 --pdf-engine=xelatex --listings -H listings-setup.tex --toc -V geometry:"left=1cm, top=1cm, right=1cm, bottom=1cm" -V fontsize=12pt "$source.md" -o "$target.html"
    pandoc --pdf-engine=xelatex --listings -H listings-setup.tex --toc -V geometry:"left=1cm, top=1cm, right=1cm, bottom=1cm" -V fontsize=12pt "$target.html" -o "$target.pdf"
fi
