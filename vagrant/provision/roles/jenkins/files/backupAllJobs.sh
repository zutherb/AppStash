#!/bin/bash
cp -f /var/lib/jenkins/config.xml /provision/roles/jenkins/files/config.xml

echo "Download Jenkins Cli"
wget http://localhost:8080/jnlpJars/jenkins-cli.jar -O /tmp/jenkins-cli.jar

echo "save Jobs"
java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 list-jobs &> /provision/roles/jenkins/files/jobs.txt
for BUILD in $(cat /provision/roles/jenkins/files/jobs.txt)
do
	java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 get-job $BUILD &> /provision/roles/jenkins/files/$BUILD.xml
done