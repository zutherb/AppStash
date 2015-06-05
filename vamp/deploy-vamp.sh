#!/usr/bin/env bash
curl -v -H "Content-Type: application/json" -X POST --data @vamp-router.json http://10.141.141.10:8080/v2/apps
