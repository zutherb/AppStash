#!/bin/sh
mkdir ~/.ssh/
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/authorized_keys
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/known_hosts
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/id_rsa
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/id_rsa.pub

