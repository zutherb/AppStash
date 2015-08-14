#!/usr/bin/env bash

sudo apt-get install -y python-software-properties software-properties-common \
    postgresql \
    python-software-properties

sudo cp /vagrant/scripts/vamp/postgresql.conf /etc/postgresql/9.3/main/postgresql.conf
sudo service postgresql restart
sudo -u postgres psql /vagrant/scripts/vamp/init_vamp_database.sql

sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get update
sudo apt-get install -y oracle-java8-installer
sudo apt-get install -y oracle-java8-set-default

sudo add-apt-repository -y ppa:vbernat/haproxy-1.5
sudo apt-get update
sudo apt-get install -y haproxy

REPO_ENTRY="deb https://dl.bintray.com/magnetic-io/upstart trusty main"
if ! grep --quiet "${REPO_ENTRY}" /etc/apt/sources.list; then
    echo $REPO_ENTRY | sudo tee -a /etc/apt/sources.list
fi

curl https://bintray.com/user/downloadSubjectPublicKey?username=bintray | sudo apt-key add -
sudo apt-get update

sudo apt-get install -y vamp-core
sudo cp -vf /vagrant/scripts/vamp/core-application.conf /usr/share/vamp-core/conf/application.conf
sudo service vamp-core restart

sudo apt-get install -y vamp-pulse
sudo cp -vf /vagrant/scripts/vamp/pulse-application.conf /usr/share/vamp-pulse/conf/application.conf
sudo service vamp-pulse restart

sudo apt-get install -y vamp-router
/usr/share/vamp-router/vamp-router --binary=/usr/sbin/haproxy

sudo apt-get install -y vamp-cli