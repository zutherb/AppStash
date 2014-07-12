#!/bin/bash
cp -f /provision/ssh/id_rsa /home/vagrant/.ssh/id_rsa
cp -f /provision/ssh/id_rsa.pub /home/vagrant/.ssh/id_rsa.pub

sudo cp -f /provision/etc/hosts /etc/hosts
sudo cp -f /provision/apt/sources.list /etc/apt/sources.list

wget -q -O - http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo sh -c 'echo deb http://downloads.sourceforge.net/project/sonar-pkg/deb binary/ > /etc/apt/sources.list.d/sonar.list'
sudo apt-get --allow-unauthenticated update
sudo apt-get -y -q --allow-unauthenticated install jenkins git sonar gnome-core xfce4 firefox nano vnc4server postgresql

cd /usr/local/
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u5-b13/jdk-8u5-linux-x64.tar.gz"
sudo tar xzf jdk-8u5-linux-x64.tar.gz
sudo rm jdk-8u5-linux-x64.tar.gz
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/local/jdk1.8.0_05/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/local/jdk1.8.0_05/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/local/jdk1.8.0_05/bin/javaws" 1

sudo -u postgres psql -c "CREATE ROLE sonar LOGIN UNENCRYPTED PASSWORD 'sonar' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;"
sudo -u postgres psql -c "CREATE DATABASE sonar WITH OWNER = sonar TEMPLATE = template0 ENCODING = 'UTF-8' TABLESPACE = pg_default LC_COLLATE = 'C' LC_CTYPE = 'C' CONNECTION LIMIT = -1;"

sudo cp -f /provision/sonar/sonar.properties /opt/sonar/conf/sonar.properties
sudo service sonar restart
sudo update-rc.d sonar defaults

sudo cp -f /provision/etc/X11/Xwrapper.config /etc/X11/Xwrapper.config
sudo cp -f /provision/etc/init.d/vncserver /etc/init.d/vncserver
sudo update-rc.d vncserver defaults

sudo su jenkins
cd ~
mkdir ~/.vnc/
cp -f /provision/vnc/* ~/.vnc/
cp /provision/jenkins/config.xml /var/lib/jenkins/config.xml
wget http://localhost:8080/jnlpJars/jenkins-cli.jar
java -jar jenkins-cli.jar -s http://localhost:8080/ install-plugin git git-client chucknorris greenballs build-pipeline-plugin copyartifact performance jacoco buildgraph-view xvnc
cat /provision/jenkins/pizza-build.xml | java -jar jenkins-cli.jar -s http://localhost:8080/ create-job pizza-build
cat /vagrant/provision/jenkins/pizza-test-deployment.xml | java -jar jenkins-cli.jar -s http://localhost:8080/ create-job pizza-test-deployment
java -jar jenkins-cli.jar -s http://localhost:8080/ restart
java -jar jenkins-cli.jar -s http://localhost:8080/ build pizza-build
cp -f /provision/ssh/id_rsa ~/.ssh/id_rsa
cp -f /provision/ssh/id_rsa.pub ~/.ssh/id_rsa.pub
chmod 600 ~/.ssh/id_rsa
chmod 600 ~/.ssh/id_rsa.pub

