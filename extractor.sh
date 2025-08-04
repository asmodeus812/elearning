#!/bin/bash

# sudo apt install pandoc texlive-xetex ffmpeg jq
# find cracking-coding-interview/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -P 8 -I {} sh -c './generator.sh {} ctci'
# find misc-tech-topic/ -type f -name '*.md'           | sed -e 's/\.md$//' | xargs -P 8 -I {} sh -c './generator.sh {} tech'
# find java-se-study/ -type f -name '*.md'             | sed -e 's/\.md$//' | xargs -P 8 -I {} sh -c './generator.sh {} study'
# find java-se-study/ -type f -name '*.md'             | sed -e 's/\.md$//' | xargs -P 2 -I {} sh -c './converter.sh {} study'

# find cracking-coding-interview/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -P 2 -I {} sh -c './converter.sh {} ctci'
find misc-tech-topic/ -maxdepth 1 -type f -name '*.md'           | sed -e 's/\.md$//' | xargs -P 2 -I {} sh -c './converter.sh {} tech'
