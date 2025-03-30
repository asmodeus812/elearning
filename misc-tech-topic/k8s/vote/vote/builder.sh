#!/bin/bash

echo "Building the vote frontend image"
docker image build -t vote-frontend:latest .
