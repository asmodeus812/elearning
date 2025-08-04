#!/bin/bash

if [ $# -lt 2 ]; then
    echo "Usage $0 {input-file} export_folder"
    exit 1
fi

input="$1"
target="$(pwd "$input")/exports/$2/$(basename "$input")"
mkdir -p "$(pwd)/exports/$2" && echo "Processing $input"

pandoc -t html5 --pdf-engine=xelatex --listings -H listings-setup.tex --toc -V geometry:"left=1cm, top=1cm, right=1cm, bottom=1cm" -V fontsize=12pt "$input.md" -o "$target.html"
pandoc --pdf-engine=xelatex --listings -H listings-setup.tex --toc -V geometry:"left=1cm, top=1cm, right=1cm, bottom=1cm" -V fontsize=12pt "$target.html" -o "$target.pdf"
