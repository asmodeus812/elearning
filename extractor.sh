#!/bin/bash

# sudo apt install pandoc texlive-xetex
find cracking-coding-interview/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -I {} sh -c './generator.sh {} ccti'
find misc-tech-topic/ -type f -name '*.md'           | sed -e 's/\.md$//' | xargs -I {} sh -c './generator.sh {} tech'
find java-se-study/ -type f -name '*.md'             | sed -e 's/\.md$//' | xargs -I {} sh -c './generator.sh {} study'
