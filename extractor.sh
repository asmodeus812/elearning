#!/bin/bash

# sudo apt install pandoc texlive-xetex ffmpeg jq
echo "Generating cracking-coding-interview/"
find cracking-coding-interview/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -P 8 -I {} sh -c './generator.sh {}'

echo "Generating java-se-study/"
find java-se-study/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -P 8 -I {} sh -c './generator.sh {}'

echo "Generating misc-tech-topic/"
find misc-tech-topic/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -P 8 -I {} sh -c './generator.sh {}'

echo "Converting cracking-coding-interview/"
find cracking-coding-interview/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -P 2 -I {} sh -c './converter.sh {}'

echo "Converting java-se-study/"
find java-se-study/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -P 2 -I {} sh -c './converter.sh {}'

echo "Converting misc-tech-topic/"
find misc-tech-topic/ -maxdepth 1 -type f -name '*.md' | sed -e 's/\.md$//' | xargs -P 2 -I {} sh -c './converter.sh {}'
