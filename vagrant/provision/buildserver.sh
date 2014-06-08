#!/bin/bash
wget -O /home/vagrant/.ssh/id_rsa https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/id_rsa
wget -O /home/vagrant/.ssh/id_rsa.pub https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/id_rsa.pub

sudo rm /etc/hosts
cd /etc
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/etc/hosts

sudo rm /etc/apt/sources.list
cd /etc/apt/
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/apt/sources.list

wget -q -O - http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo sh -c 'echo deb http://downloads.sourceforge.net/project/sonar-pkg/deb binary/ > /etc/apt/sources.list.d/sonar.list'
sudo apt-get --allow-unauthenticated update
sudo apt-get -y -q --allow-unauthenticated install jenkins git sonar gnome-core xfce4 firefox nano vnc4server postgresql

sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u5-b13/jdk-8u5-linux-x64.tar.gz"
sudo tar xzf jdk-8u5-linux-x64.tar.gz
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/local/jdk1.8.0_05/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/local/jdk1.8.0_05/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/local/jdk1.8.0_05/bin/javaws" 1

sudo -u postgres psql -c "CREATE ROLE sonar LOGIN UNENCRYPTED PASSWORD 'sonar' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;"
sudo -u postgres psql -c "CREATE DATABASE sonar WITH OWNER = sonar TEMPLATE = template0 ENCODING = 'UTF-8' TABLESPACE = pg_default LC_COLLATE = 'C' LC_CTYPE = 'C' CONNECTION LIMIT = -1;"

cd /opt/sonar/conf
sudo rm sonar.properties
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/sonar/sonar.properties
sudo service sonar restart
sudo update-rc.d sonar defaults

sudo su jenkins
cd ~
wget -qO - https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/jenkins/config.xml >> /var/lib/jenkins/config.xml
wget http://localhost:8080/jnlpJars/jenkins-cli.jar
java -jar jenkins-cli.jar -s http://localhost:8080/ install-plugin git greenballs build-pipeline-plugin copyartifact performance jacoco -restart
wget -qO - https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/jenkins/pizza-build.xml | java -jar jenkins-cli.jar -s http://localhost:8080/ create-job pizza-build
wget -qO - https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/jenkins/pizza-test-deployment.xml | java -jar jenkins-cli.jar -s http://localhost:8080/ create-job pizza-test-deployment
java -jar jenkins-cli.jar -s http://localhost:8080/ restart
java -jar jenkins-cli.jar -s http://localhost:8080/ build pizza-build

wget -O ~/.ssh/id_rsa https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/id_rsa
wget -O ~/.ssh/id_rsa.pub https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/ssh/id_rsa.pub
chmod 600 ~/.ssh/id_rsa
chmod 600 ~/.ssh/id_rsa.pub
exit

