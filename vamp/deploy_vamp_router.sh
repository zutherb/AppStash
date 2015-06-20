#!/usr/bin/env bash
curl -v -H "Content-Type: application/json" -X POST --data @vamp-router.json http://172.31.1.11:8080/v2/apps


