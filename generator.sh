#!/bin/bash

pandoc --listings -H listings-setup.tex --toc -V geometry:"left=1cm, top=1cm, right=1cm, bottom=2cm" -V fontsize=12pt "$1.md" -o "$1.pdf"

