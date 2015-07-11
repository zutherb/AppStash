#!/usr/bin/env bash
curl -v -X POST --data-binary @shop.yml -H "Content-Type: application/x-yaml" http://$(boot2docker ip):8081/api/v1/deployments
