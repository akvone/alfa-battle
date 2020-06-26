#!/bin/sh

mvn clean package
docker build -t alfa-battle .
docker run -it -p 8080:8080 alfa-battle