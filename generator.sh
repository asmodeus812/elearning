#!/bin/bash

mkdir -p "$(pwd)/exports/$2"
pandoc --listings -H listings-setup.tex --toc -V geometry:"left=1cm, top=1cm, right=1cm, bottom=1cm" -V fontsize=12pt "$1.md" -o "$(pwd $1)/exports/$2/$(basename $1).pdf"
