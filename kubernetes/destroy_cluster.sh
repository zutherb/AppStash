#!/bin/sh
cd ./vagrant/
NODE_MEM=1536 NODE_CPUS=1 NUM_INSTANCES=3 vagrant destroy
cd ..