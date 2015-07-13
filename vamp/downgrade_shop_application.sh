#!/usr/bin/env bash
export BOOT2DOCKER_URL=http://$(boot2docker ip)
export APP_NAME=$(curl -s $BOOT2DOCKER_URL:8081/api/v1/deployments | jq '.[0].name' | sed -e 's/^"//'  -e 's/"$//')
echo "APP_NAME=$APP_NAME"

curl -v -X PUT --data-binary @shop.yml -H "Content-Type: application/x-yaml" http://$(boot2docker ip):8081/api/v1/deployments/$APP_NAME
curl -v -X DELETE -H "Content-Type: application/x-yaml" http://192.168.59.103:8081/api/v1/breeds/catalog_with_alternative_checkout_design