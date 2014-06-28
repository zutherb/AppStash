#!/bin/bash
wget -O /home/vagrant/.ssh/id_rsa https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/id_rsa
wget -O /home/vagrant/.ssh/id_rsa.pub https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/id_rsa.pub

sudo rm /etc/hosts
cd /etc
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/etc/hosts

sudo rm /etc/apt/sources.list
cd /etc/apt/
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/apt/sources.list

sudo apt-get update
sudo apt-get install unzip htop -y  -q
cd /usr/local/
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u5-b13/jdk-8u5-linux-x64.tar.gz"
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/7u60-b19/jdk-7u60-linux-x64.tar.gz"
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/6u45-b06/jdk-6u45-linux-x64.bin"

sudo chmod +x jdk-6u45-linux-x64.bin
sudo ./jdk-6u45-linux-x64.bin
sudo rm -rf jdk-6u45-linux-x64.bin

sudo tar xzf jdk-7u60-linux-x64.tar.gz
sudo tar xzf jdk-8u5-linux-x64.tar.gz
sudo rm -f *.tar.gz

sudo update-alternatives --install "/usr/bin/java" "java" "/usr/local/jdk1.8.0_05/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/local/jdk1.8.0_05/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/local/jdk1.8.0_05/bin/javaws" 1

sudo update-alternatives --install "/usr/bin/java" "java" "/usr/local/jdk1.7.0_60/bin/java" 2
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/local/jdk1.7.0_60/bin/javac" 2
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/local/jdk1.7.0_60/bin/javaws" 2

sudo update-alternatives --install "/usr/bin/java" "java" "/usr/local/jdk1.6.0_45/bin/java" 3
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/local/jdk1.6.0_45/bin/javac" 3
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/local/jdk1.6.0_45/bin/javaws" 3

wget http://bernd-zuther.de/files/memory-leak.zip