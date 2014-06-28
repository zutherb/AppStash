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
sudo apt-get install tomcat7 tomcat7-admin python-software-properties git links curl mongodb mongodb-clients mongodb-server unzip ttf-dejavu -y  -q
cd /usr/local/
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u5-b13/jdk-8u5-linux-x64.tar.gz"
sudo tar xzf jdk-8u5-linux-x64.tar.gz
sudo update-alternatives --install "/usr/bin/java" "java" "/usr/local/jdk1.8.0_05/bin/java" 1
sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/local/jdk1.8.0_05/bin/javac" 1
sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/local/jdk1.8.0_05/bin/javaws" 1

sudo /etc/default/tomcat7
sudo cat > /etc/default/tomcat7  <<EOF
TOMCAT7_USER=tomcat7
TOMCAT7_GROUP=tomcat7
JAVA_HOME=/usr/local/jdk1.8.0_05
JAVA_OPTS="-Djava.awt.headless=true -Xmx1024M -Xms512M"
CATALINA_OPTS="-Dcom.sun.management.jmxremote.port=11001 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
EOF

sudo rm /var/lib/tomcat7/conf/server.xml
sudo rm /var/lib/tomcat7/conf/tomcat-users.xml
cd /var/lib/tomcat7/conf/
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/tomcat/server.xml
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/tomcat/tomcat-users.xml

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
cd /etc/logstash/
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/logstash/logstash.conf
cd /etc/init.d/
sudo wget https://raw.githubusercontent.com/zutherb/AppStash/master/vagrant/provision/logstash/logstash
sudo chmod +x /etc/init.d/logstash
sudo update-rc.d logstash defaults
sudo service logstash start

sudo service tomcat7 restart