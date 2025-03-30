#!/bin/bash

target="$(pwd $1)/exports/$2/$(basename $1)"
mkdir -p "$(pwd)/exports/$2" && echo "Processing: $target"
pandoc -t html5 --pdf-engine=xelatex --listings -H listings-setup.tex --toc -V geometry:"left=1cm, top=1cm, right=1cm, bottom=1cm" -V fontsize=12pt "$1.md" -o "$target.html"
pandoc --pdf-engine=xelatex --listings -H listings-setup.tex --toc -V geometry:"left=1cm, top=1cm, right=1cm, bottom=1cm" -V fontsize=12pt "$target.html" -o "$target.pdf"
rm -rf "$target.html"
