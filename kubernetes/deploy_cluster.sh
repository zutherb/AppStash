#!/bin/sh
../../kubernetes/cluster/kubectl.sh create -f redis.json
../../kubernetes/cluster/kubectl.sh create -f redis-service.json
../../kubernetes/cluster/kubectl.sh create -f cart.json
../../kubernetes/cluster/kubectl.sh create -f cart-service.json
../../kubernetes/cluster/kubectl.sh create -f mongodb.json
../../kubernetes/cluster/kubectl.sh create -f mongodb-service.json
../../kubernetes/cluster/kubectl.sh create -f product.json
../../kubernetes/cluster/kubectl.sh create -f product-service.json
../../kubernetes/cluster/kubectl.sh create -f navigation.json
../../kubernetes/cluster/kubectl.sh create -f navigation-service.json
../../kubernetes/cluster/kubectl.sh create -f shop.json
../../kubernetes/cluster/kubectl.sh create -f shop-service.json
../../kubernetes/cluster/kubectl.sh create -f catalog.json
../../kubernetes/cluster/kubectl.sh create -f catalog-service.json
