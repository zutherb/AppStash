#!/usr/bin/env bash
curl -v -X DELETE -H "Content-Type: application/x-yaml" http://$(boot2docker ip):8081/api/v1/deployments/ae5c917e-db1e-4b94-8bb5-6ff05c5c9100