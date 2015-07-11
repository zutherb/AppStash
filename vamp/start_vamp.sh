#!/usr/bin/env bash
export MARATHON_MASTER=172.31.1.11
export VAMP_MARATHON_URL=http://$MARATHON_MASTER:8080
export VAMP_ROUTER_HOST=$(curl -s $VAMP_MARATHON_URL/v2/apps/main-router | jq '.app.tasks[0].host' | sed -e 's/^"//'  -e 's/"$//')
export VAMP_ROUTER_URL=http://$VAMP_ROUTER_HOST:10001

echo "VAMP_MARATHON_URL is: $VAMP_MARATHON_URL"
echo "VAMP_ROUTER_URL is: $VAMP_ROUTER_URL"

docker run -d \
    --name=vamp \
    -p 81:80 \
    -p 9200:9200 \
    -p 8081:8080 \
    -p 10002:10001 \
    -p 8084:8083 \
    -e VAMP_MARATHON_URL=$VAMP_MARATHON_URL \
    -e VAMP_ROUTER_URL=$VAMP_ROUTER_URL \
    -e VAMP_ROUTER_HOST=$VAMP_ROUTER_HOST \
    magneticio/vamp-mesosphere:latest

docker run -d -p 5601:5601 --name kibana --link vamp:elasticsearch deviantony/elk-kibana

#docker logs -f vamp
