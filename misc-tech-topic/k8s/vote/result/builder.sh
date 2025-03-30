#!/bin/bash

echo "Building the vote results image"
docker image build -t vote-results:latest .
