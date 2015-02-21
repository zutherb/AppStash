#!/bin/sh
../../kubernetes/cluster/kubectl.sh create -f redis.json
../../kubernetes/cluster/kubectl.sh create -f cart.json
../../kubernetes/cluster/kubectl.sh create -f mongodb.json
../../kubernetes/cluster/kubectl.sh create -f product.json
../../kubernetes/cluster/kubectl.sh create -f navigation.json
../../kubernetes/cluster/kubectl.sh create -f shop.json
../../kubernetes/cluster/kubectl.sh create -f catalog.json
