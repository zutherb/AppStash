#!/usr/bin/env bash
export BOOT2DOCKER_URL=http://$(boot2docker ip)
export DEPLOYMENT_NAME=$(curl -s http://172.31.1.11:8081/api/v1/deployments | gunzip - | jq '.[0].name' | sed -e 's/^"//'  -e 's/"$//')
echo "DEPLOYMENT_NAME=DEPLOYMENT_NAME"

curl -v -X PUT --data-binary @shop-ab-test.yml -H "Content-Type: application/x-yaml" http://172.31.1.11:8081/api/v1/deployments/$DEPLOYMENT_NAME | gunzip -