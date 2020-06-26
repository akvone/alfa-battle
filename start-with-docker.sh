#!/bin/sh

docker build -t alfa-battle .
docker run -p 8080:8080 alfa-battle