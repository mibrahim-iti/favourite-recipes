#! /bin/bash

docker build --no-cache -t favourite-recipes-image -f infrastructure/docker/Dockerfile .
docker rmi -f $(docker images -f 'dangling=true' -q)
