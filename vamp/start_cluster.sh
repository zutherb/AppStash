#!/usr/bin/env bash
boot2docker up
cd vagrant
vagrant up
cd ..
./start_vamp.sh