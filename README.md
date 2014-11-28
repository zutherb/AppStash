# Microservice Phone Shop Application

## Overview

This application gives architects and developers an example how a microservice web application architecture can
look like. The application is based on the **AngularJS Phone Catalog** and **MongoDB Pizza Shop**, which can be found
on Github: 
- [AngularJS Phone Catalog Tutorial Application](https://github.com/angular/angular-phonecat)
- [MongoDB Pizza Shop](https://github.com/comsysto/mongodb-onlineshop)

The application shows a small online shop for mobile devices that implements the following use cases. An user is able to:
- see different catalogs of mobile devices,
- create a cart,
- and order the created cart.

![Use Case Online Shop](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/use_case_online_shop.png)

This use cases implemented in to way:

- [Monolith Webshop](https://github.com/zutherb/AppStash/#monolith-appserver), which represents a three layer architecture based online shop that is  Wicket
- [Microservice Webshop](https://github.com/zutherb/AppStash/#microservice-appserver), which represents a microservice architecture based online shop on AngularJS, Wicket, Scala, Restx and Spring Boot


## Directory Layout

    microservice/           --> all files of the microservice applications are located in this folder
        frontend/           --> all frontend applications are located in this folder
            catalog/        --> an angularjs frontend application that shows the product catalog and is used to create a cart is located in this directory
            registration/   --> all files that are needed to glue the registration form of the monolithic with the microservice application
        service/            --> all business services are located in the folder
            cart/           --> a spring boot cart rest service is located in the folder
            dataloader/     --> a spring boot application that prepares the initial mongodb dataset is located in the folder
            navigation/     --> a java rest x navigation rest service is located in the folder
            order/          --> a java spark order rest service is located in the folder
            product/        --> a scala spray product rest service is located in the folder
            recommendation/ --> a R recommendation rest service is located in the folder
            user/           --> a spring boot user rest service is located in the folder
    monolithic/             --> all files of the monolithic application are located in this directory

## Prerequisites

You need some dependencies to run the application cluster or to extend the application.

###Running 

You need at least 16 GB RAM to run the whole cluster and you have to install the following software dependencies
on your machine.

#### Git

- A good place to learn about setting up git is [here](https://help.github.com/articles/set-up-git)
- Git [home](http://git-scm.com/) (download, documentation)

#### Vagrant

- Vagrant creates and configures lightweight, reproducible, and portable development environments. You do not have to
learn much about Vagrant, but you should be able to install it and execute the following commandline: ```vagrant up```
- [Vagrant](https://www.vagrantup.com/) (download, documentation)

#### Ansible

- Ansible is the simplest way to automate IT. You must not know Ansible, but you have to install Ansible
  otherwise Vagrant is not able to create the virtual machines.
- [Ansible](http://www.ansible.com/) (download, documentation)

###Boot up the cluster

You only have to execute the following command if you want to run a development cluster:

```bash
git clone git@github.com:zutherb/AppStash.git appstash
cd appstash
cd vagrant
vagrant plugin install vagrant-cachier
vagrant plugin install vagrant-hostsupdater
vagrant up
```

## Workings with the application cluster

The Cluster contains of the following nodes:

Vargrant-Name | IP            | Hostname           | Application        | Forward
--------------|---------------|--------------------|--------------------|------------------------
buildserver   | 10.211.55.200 | ci-node            | Jenkins            | http://ci.microservice.io:8080/
reposerver    | 10.211.55.201 | ci-repo            | Artifact Repository (NGINX) |
dbserver      | 10.211.55.202 | mongodb-node       | MongoDB            | localhost:27017
dbserver      | 10.211.55.202 | redis-node         | Redis              | localhost:6379
appserver1    | 10.211.55.101 | app-server-node-1  | Legacy Shop        | http://test.monolith.io:8080/shop/
appserver1    | 10.211.55.101 | app-server-node-1  | Probe              | http://test.monolith.io:8080/probe/ (admin / topsecret)
appserver2    | 10.211.55.102 | app-server-node-2  | Legacy Shop        | http://shop.monolith.io:8080/shop/
appserver2    | 10.211.55.102 | app-server-node-2  | Probe              | http://shop.monolith.io:8080/probe/ (admin / topsecret)
appserver3    | 10.211.55.103 | app-server-node-3  | Microservice Shop  | http://test-shop.microservice.io/
appserver3    | 10.211.55.104 | app-server-node-4  | Microservice Shop  | http://shop.microservice.io/
elasticsearch | 10.211.55.100 | monitoring-node    | Kibana             | http://monitoring.microservice.io/
elasticsearch | 10.211.55.100 | monitoring-node    | Nagios             | http://monitoring.microservice.io/nagios3/ (nagiosadmin / admin123)
elasticsearch | 10.211.55.100 | monitoring-node    | Icinga             | http://monitoring.microservice.io/icinga/ (icingaadmin / admin123)

###CI-Node

A Jenkins build server is running on the CI-Node. Jenkins is an open source continuous integration tool written in Java.
Jenkins provides continuous integration services for software development and supports SCM tools including CVS,
Subversion, Git and Mercurial, and can execute Apache Ant, Apache Maven and Gradle based projects as well as arbitrary
shell scripts and Windows batch commands.

You can reach the jenkins that builds and deploy the monolith and microservice application under http://ci.microservice.io:8080/.

![CI-Node](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/ci-node.png)

###Monolith Appserver

A three layer architecture online shop based on Apache Wicket is deployed on the Monolith Appserver which is a reference
implementation for the use cases given in the [Overview](https://github.com/zutherb/AppStash/#monolith-appserver). You
can reach the online shop under the following url http://shop.monolith.io:8080/shop/ .

![Monolith Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/monolith-appserver.png)

A [PSI Probe](https://code.google.com/p/psi-probe/) is reachable under http://shop.monolith.io:8080/probe/
(admin / topsecret) which is a community-driven fork of Lambda Probe. It is intended to replace and extend Tomcat
Manager, making it easier to manage and monitor an instance of Apache Tomcat.

Unlike many other server monitoring tools, PSI Probe does not require any changes to your existing apps. It provides
all of its features through a web-accessible interface that becomes available simply by deploying it to your server.
These features include:

- Requests: Monitor traffic in real-time, even on a per-application basis.
- Sessions: Browse/search attributes, view last IP, expire, estimate size.
- JSP: Browse, view source, compile.
- Data Sources: View pool usage, execute queries.
- Logs: View contents, download, change levels at runtime.
- Threads: View execution stack, kill.
- Connectors: Status, usage charts.
- Cluster: Status, usage charts.
- JVM: Memory usage charts, advise GC
- Java Service Wrapper: Restart JVM.
- System: CPU usage, memory usage, swap file usage.

![Probe](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/probe.png)
![Probe](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/probe-log.png)

###Microservice Appserver

![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/microservice-appserver.png)

###Monitoring Server

Icinga is an open source network and computer system monitoring application. It was originally created as a fork of
the Nagios system monitoring application. Icinga is attempting to get past perceived short-comings in Nagios development
process, as well as adding new features such as a modern Web 2.0 style user interface, additional database connectors,
and a REST API that lets administrators integrate numerous extensions without complicated modification of the Icinga core.

![Icinga Status Map](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/icinga-status-map.png)
![Icinga Status Report](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/icinga-status.png)

Kibana is a browser based analytics and search interface for Elasticsearch that was developed primarily to view
Logstash event data. Logstash is a tool that can be used to collect, process and forward events and log messages.
Collection is accomplished via number of configurable input plugins including raw socket/packet communication,
file tailing and several message bus clients. Once an input plugin has collected data it can be processed by any number
of filters which modify and annotate the event data. Finally events are routed to output plugins which can forward the
events to a variety of external programs including Elasticsearch, local files and several message bus implementations.

![Kibana](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/kibana.png)

## Contact

[Bernd Zuther] (mailto:bernd.zuther@codecentric.de)

