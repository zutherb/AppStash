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

    microservice/       --> all files of the microservice applications are located in this directory
        frontend/       --> all frontend applications are located in this directory
            catalog/    --> an angularjs frontend application that shows the product catalog and is used to create a cart is located in this directory
            checkout/   --> the react frontend application that that is used to create an order is located in this directory
        service/        --> css files
    monolithic/         --> all files of the monolithic applications are located in this directory
        common/         --> css files
        repository/     --> css files
        service/        --> css files
        ui/             --> css files

## Prerequisites

You need same dependencies to run the application or to extends the application.

###Running 

If you want to run the application you has to install the following programs on our machine.

#### Git

- A good place to learn about setting up git is [here](https://help.github.com/articles/set-up-git)
- Git [home](http://git-scm.com/) (download, documentation)

#### Vagrant

- You must be not learn much about vagrant, but you should be able to install vagrant and execute the following 
  commandline: vagrant up
- [Vagrant](https://www.vagrantup.com/) (download, documentation)

#### Ansible

- You must not know Ansible, but it must be to installed that vagrant be create virtual machines.
- [Ansible](http://www.ansible.com/) (download, documentation)

#### Apt-Cacher-Ng

- Apt-Cacher-Ng runs on your Mac without opening port 80 to everyone on the internet and become some zombie relay.
- [Apt-Cacher-Ng](https://github.com/stephen-mw/mac-apt-cacher) (download, documentation)

Installation:
```bash
brew install apt-cacher-ng
sudo cp -fv /usr/local/opt/apt-cacher-ng/*.plist /Library/LaunchDaemons
sudo launchctl load /Library/LaunchDaemons/homebrew.mxcl.apt-cacher-ng.plist
```

####Run the Cluster 

You only have to execute the following command if you want to run a development cluster: 

```bash
git clone git@github.com:zutherb/AppStash.git appstash
cd appstash
cd vagrant
vagrant up
```

The Cluster contains of the following nodes:

Vargrant-Name | IP            | Hostname           | Application        | Forward
--------------|---------------|--------------------|--------------------|------------------------
buildserver   | 10.211.55.200 | ci-node            | Jenkins            |http://localhost:10000/
buildserver   | 10.211.55.200 | ci-node            | Sonar              |http://localhost:9000/
reposerver    | 10.211.55.201 | ci-repo            | Artifact Repository (NGINX) |
dbserver      | 10.211.55.202 | mongodb-node       | MongoDB            | localhost:27017
appserver1    | 10.211.55.101 | app-server-node-1  | Legacy Shop        | localhost:8080/shop/
appserver1    | 10.211.55.101 | app-server-node-1  | Probe              | localhost:8080/probe/ (admin / topsecret)
appserver2    | 10.211.55.102 | app-server-node-2  | Legacy Shop        | localhost:8081/shop/
appserver2    | 10.211.55.102 | app-server-node-2  | Probe              | localhost:8081/probe/ (admin / topsecret)
appserver3    | 10.211.55.103 | app-server-node-3  | Microservice Shop  | localhost:8082/
elasticsearch | 10.211.55.100 | elasticsearch-node | Kibana             | http://localhost:8000/
elasticsearch | 10.211.55.100 | elasticsearch-node | Nagios             | http://localhost:8000/nagios3/ (nagiosadmin / admin123)

## Workings with the application


### Installing dependencies

##Deployment Pipeline

![VNC View](https://raw.githubusercontent.com/zutherb/AppStash/ci-server/external/images/vnc-jenkins.png)

## Contact

[Bernd Zuther] (mailto:bernd.zuther@codecentric.de)

