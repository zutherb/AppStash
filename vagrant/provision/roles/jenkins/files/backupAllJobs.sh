#!/bin/bash
wget http://localhost:8080/jnlpJars/jenkins-cli.jar -O /tmp/jenkins-cli.jar
for BUILD in $(java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 list-jobs)
do
	java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 get-job $BUILD &> /provision/roles/jenkins/files/$BUILD.xml
done