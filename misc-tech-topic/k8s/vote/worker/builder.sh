#!/bin/bash

echo "Building the vote worker image"
docker image build -t vote-worker:latest .
