#!/bin/bash
cp -f /provision/ssh/id_rsa /home/vagrant/.ssh/id_rsa
cp -f /provision/ssh/id_rsa.pub /home/vagrant/.ssh/id_rsa.pub
sudo cp -f /provision/etc/hosts /etc/hosts
sudo cp -f /provision/apt/sources.list /etc/apt/sources.list

sudo apt-get update
sudo apt-get install tomcat7 tomcat7-admin python-software-properties git links curl mongodb mongodb-clients mongodb-server unzip ttf-dejavu -y  -q
cd /usr/local/
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u5-b13/jdk-8u5-linux-x64.tar.gz"
sudo tar xzf jdk-8u5-linux-x64.tar.gz
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/local/jdk1.8.0_05/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/local/jdk1.8.0_05/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/local/jdk1.8.0_05/bin/javaws" 1

sudo cp -f /provision/tomcat/default /etc/default/tomcat7
sudo cp -f /provision/tomcat/server.xml /var/lib/tomcat7/conf/server.xml
sudo cp -f /provision/tomcat/tomcat-users.xml /var/lib/tomcat7/conf/tomcat-users.xml

cd ~
wget https://psi-probe.googlecode.com/files/probe-2.3.3.zip
unzip probe-2.3.3.zip
sudo mv probe.war /var/lib/tomcat7/webapps/

cd ~
sudo wget -O /var/lib/tomcat7/webapps/pizza.war http://ci-node:8080/job/pizza-build/lastSuccessfulBuild/artifact/shop/ui/build/libs/ui-0.4-DEV.war
sudo service tomcat7 restart

cd ~
wget https://download.elasticsearch.org/logstash/logstash/logstash-1.4.1.tar.gz
tar xvf logstash-1.4.1.tar.gz
rm logstash-1.4.1.tar.gz
sudo mv logstash-1.4.1 /usr/local/logstash

sudo mkdir /etc/logstash/
sudo cp -f provision/logstash/logstash.conf /etc/logstash/
sudo cp -f /provision/logstash/logstash /etc/init.d/logstash
sudo chmod +x /etc/init.d/logstash
sudo update-rc.d logstash defaults
sudo service logstash start

sudo service tomcat7 restart