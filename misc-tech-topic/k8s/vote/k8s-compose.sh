#!/bin/bash

echo "Building the project images"
cd ./worker && ./builder.sh && cd - || exit
cd ./result && ./builder.sh && cd - || exit
cd ./vote   && ./builder.sh && cd - || exit

echo "Deploying to the k8s clutser"
kubectl apply -f ./k8s-specifications

