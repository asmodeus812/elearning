#!/bin/bash

find misc-tech-topic/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -I {} sh -c './generator.sh {} tech'
find java-se-study/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -I {} sh -c './generator.sh {} study'
find cracking-coding-interview/ -type f -name '*.md' | sed -e 's/\.md$//' | xargs -I {} sh -c './generator.sh {} ccti'
