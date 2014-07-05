#!/bin/bash
cp -f /provision/ssh/id_rsa /home/vagrant/.ssh/id_rsa
cp -f /provision/ssh/id_rsa.pub /home/vagrant/.ssh/id_rsa.pub

sudo cp -f /provision/etc/hosts /etc/hosts
sudo cp -f /provision/apt/sources.list /etc/apt/sources.list

sudo apt-get update
sudo apt-get install openjdk-7-jre-headless apache2 apache2-doc links curl -y

### Check http://www.elasticsearch.org/download/ for latest version of ElasticSearch and replace wget link below
cd ~
wget https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-1.2.1.tar.gz
tar -xf elasticsearch-1.2.1.tar.gz
rm elasticsearch-1.2.1.tar.gz
sudo mv elasticsearch-* elasticsearch
sudo mv elasticsearch /usr/local/share

wget http://github.com/elasticsearch/elasticsearch-servicewrapper/tarball/master
tar -xf *master*
sudo mv *servicewrapper*/service /usr/local/share/elasticsearch/bin/
rm -Rf *servicewrapper*
sudo /usr/local/share/elasticsearch/bin/service/elasticsearch install
sudo ln -s `readlink -f /usr/local/share/elasticsearch/bin/service/elasticsearch` /usr/local/bin/rcelasticsearch

sudo service elasticsearch start

wget https://download.elasticsearch.org/kibana/kibana/kibana-3.1.0.tar.gz
tar -xf kibana-3.1.0.tar.gz
rm kibana-3.1.0.tar.gz
sudo mv kibana-* kibana
sudo mv kibana/* /var/www/
rf -rf kibana/

sudo groupadd www
sudo adduser vagrant www
sudo chgrp www /var/www
sudo chmod g+w /var/www