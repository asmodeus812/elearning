#!/bin/bash

echo "Building the vote data-seeder image"
docker image build -t vote-dataseed:latest .
