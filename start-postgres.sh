#!/usr/bin/env bash

docker run -p 5432:5432 -e POSTGRES_PASSWORD=factor -e POSTGRES_USER=twelve -e POSTGRES_DB=twelvefactor -d postgres