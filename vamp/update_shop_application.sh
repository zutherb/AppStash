#!/usr/bin/env bash
export BOOT2DOCKER_URL=http://$(boot2docker ip)
export APP_NAME=$(curl -s $BOOT2DOCKER_URL:8081/api/v1/deployments | jq '.[0].name' | sed -e 's/^"//'  -e 's/"$//')
echo "APP_NAME=$APP_NAME"

curl -v -X PUT --data-binary @shop-ab-test.yml -H "Content-Type: application/x-yaml" $BOOT2DOCKER_URL:8081/api/v1/deployments/$APP_NAME