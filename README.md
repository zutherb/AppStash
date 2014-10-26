# Microservice Phone Shop Application

## Overview

This application gives architects and developer an example how a microservice web application architecture can 
looks like. The application is based on the **AngularJS Phone Catalog** and **MongoDB Pizza Shop**, which can be found 
on Github: 
- [AngularJS Phone Catalog Tutorial Application](https://github.com/angular/angular-phonecat)
- [MongoDB Pizza Shop](https://github.com/comsysto/mongodb-onlineshop)

The application shows a small online shop for mobile devices that implements the following use cases. An user is able to
see different catalogs of mobile devices and is able to order them.

The online shop will be deployed on the following cluster nodes:

    Catalog Application Server    --> angular frontend for catalog data and create a cart
    Checkout Application Server   --> react frontend for ordering a cart
    Backend Server                --> all services that are needed from both frontends are deployed on this server 
    
![Architecture Overview](https://raw.githubusercontent.com/zutherb/AppStash/ci-server/external/images/deployment-diagram.png)

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

You need same dependencies to run the application or to extends the application.

###Running 

If you want to run the application you has to install the following programs on our machine.

#### Git

- A good place to learn about setting up git is [here](https://help.github.com/articles/set-up-git)
- Git [home](http://git-scm.com/) (download, documentation)

#### Vagrant

- Vagrant creates and configures lightweight, reproducible, and portable development environments. You must be not learn 
  much about vagrant, but you should be able to install vagrant and execute the following commandline: ```vagrant up```
- [Vagrant](https://www.vagrantup.com/) (download, documentation)

#### Ansible

- Ansible is the simplest way to automate IT. You must not know Ansible, but it must be able to installed Ansible 
  otherwise vagrant is not able to created the virtual machines.
- [Ansible](http://www.ansible.com/) (download, documentation)

####Run the Cluster 

You only have to execute the following command if you want to run a development cluster: 

```bash
git clone git@github.com:zutherb/AppStash.git appstash
cd appstash
cd vagrant
vagrant plugin install vagrant-cachier
vagrant plugin install vagrant-hostsupdater
vagrant up
```

The Cluster contains of the following nodes:

Vargrant-Name | IP            | Hostname           | Application        | Forward
--------------|---------------|--------------------|--------------------|------------------------
buildserver   | 10.211.55.200 | ci-node            | Jenkins            | http://ci.microservice.io:10000/
buildserver   | 10.211.55.200 | ci-node            | Sonar              | http://ci.microservice.io:9000/
reposerver    | 10.211.55.201 | ci-repo            | Artifact Repository (NGINX) |
dbserver      | 10.211.55.202 | mongodb-node       | MongoDB            | localhost:27017
dbserver      | 10.211.55.202 | redis-node         | Redis              | localhost:6379
appserver1    | 10.211.55.101 | app-server-node-1  | Legacy Shop        | app-server-node-1:8080/shop/
appserver1    | 10.211.55.101 | app-server-node-1  | Probe              | app-server-node-1:8080/probe/ (admin / topsecret)
appserver2    | 10.211.55.102 | app-server-node-2  | Legacy Shop        | shop.monolith.io:8081/shop/
appserver2    | 10.211.55.102 | app-server-node-2  | Probe              | shop.monolith.io:8081/probe/ (admin / topsecret)
appserver3    | 10.211.55.103 | app-server-node-3  | Microservice Shop  | test-shop.microservice.io/
appserver3    | 10.211.55.104 | app-server-node-4  | Microservice Shop  | shop.microservice.io/
elasticsearch | 10.211.55.100 | elasticsearch-node | Kibana             | http://monitoring.microservice.io/
elasticsearch | 10.211.55.100 | elasticsearch-node | Nagios             | http://monitoring.microservice.io/nagios3/ (nagiosadmin / admin123)

## Workings with the application


### Installing dependencies

##Deployment Pipeline

![VNC View](https://raw.githubusercontent.com/zutherb/AppStash/ci-server/external/images/vnc-jenkins.png)

## Contact

[Bernd Zuther] (mailto:bernd.zuther@codecentric.de)

