#!/usr/bin/env bash
cd vagrant
vagrant up
cd ..

export MARATHON_MASTER=172.31.1.11
export VAMP_MARATHON_URL=http://$MARATHON_MASTER:8080
export VAMP_ROUTER_HOST=172.31.2.11
export VAMP_ROUTER_URL=http://$VAMP_ROUTER_HOST:10001

docker run -d --name=vamp -p 81:80 -p 8081:8080 -p 10002:10001 -p 8084:8083 -e VAMP_MARATHON_URL=http://$MARATHON_MASTER:8080 -e VAMP_ROUTER_URL=http://$VAMP_ROUTER_HOST:10001 -e VAMP_ROUTER_HOST=$VAMP_ROUTER_HOST magneticio/vamp-mesosphere:latest