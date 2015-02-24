#!/bin/sh
kubectl create -f redis.json
kubectl create -f redis-service.json
kubectl create -f cart.json
kubectl create -f cart-service.json
kubectl create -f mongodb.json
kubectl create -f mongodb-service.json
kubectl create -f product.json
kubectl create -f product-service.json
kubectl create -f navigation.json
kubectl create -f navigation-service.json
kubectl create -f shop.json
kubectl create -f shop-service.json
kubectl create -f catalog.json
kubectl create -f catalog-service.json
