#!/bin/sh
brew install kubernetes-cli wget fleetctl etcdctl
git submodule init
git submodule update
cd ./vagrant/
KUBERNETES_VERSION=0.21.0 NUM_INSTANCES=3 vagrant up
cd ../example/shop/
./deploy_cluster.sh
cd ../..