#!/usr/bin/env bash
boot2docker up
cd vagrant
vagrant up
cd ..
./stop_vamp.sh
./start_vamp.sh
