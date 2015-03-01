#!/bin/sh
echo "*********************************"
echo "Setup Pods                       "
echo "*********************************"
kubectl create -f redis.json
kubectl create -f cart.json
kubectl create -f mongodb.json
kubectl create -f product.json
kubectl create -f navigation.json
kubectl create -f shop.json
kubectl create -f catalog.json
echo "*********************************"
echo "Setup Replication Controller     "
echo "*********************************"
kubectl create -f cart-controller.json
kubectl create -f product-controller.json
kubectl create -f navigation-controller.json
kubectl create -f catalog-controller.json
echo "*********************************"
echo "Setup Services                   "
echo "*********************************"
kubectl create -f redis-service.json
kubectl create -f cart-service.json
kubectl create -f mongodb-service.json
kubectl create -f product-service.json
kubectl create -f navigation-service.json
kubectl create -f shop-service.json
kubectl create -f catalog-service.json
