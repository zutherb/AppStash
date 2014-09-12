# Microservice Phone Shop Application

## Overview

This application will give architects and developer an example how a microservice web application architecture can 
looks like. The application is based on the **AngularJS Phone Catalog** and **MongoDB Pizza Shop**, which can be found 
on Github: 
- [AngularJS Phone Catalog Tutorial Application](https://github.com/angular/angular-phonecat)
- [MongoDB Pizza Shop](https://github.com/comsysto/mongodb-onlineshop)

The application shows a small online shop for mobile devices that implements the following use cases. An user is able to
see different catalogs of mobile devices and is able to order them.

In a future phase the online shop will be deployed on the following cluster nodes:

    Catalog Application Server    --> angular frontend for catalog data and create a basket
    Checkout Application Server   --> react frontend for ordering a basket
    Backend Server                --> all services that are needed from both frontends are deployed on this server 
    
![Architecture Overview](https://raw.githubusercontent.com/zutherb/AppStash/ci-server/external/images/deployment-diagram.png)

## Prerequisites

###Running 

### Vagrant

- You must be not learn much about vagrant, but you should be able to install vagrant and execute the following 
  commandline: vagrant up
- [Vagrant](https://www.vagrantup.com/) (download, documentation)

### Ansible

- You must not know Ansible, but it must be to installed that vagrant be create virtual machines.
- [Ansible](http://www.ansible.com/) (download, documentation)

###Run the Cluster 

You only have to execute the following command if you want to run a development cluster: 

```bash
git clone git@github.com:zutherb/AppStash.git appstash
cd appstash
cd vagrant
vagrant up
```

The Cluster contains of the following nodes:

Name        | IP            | Hostname | Application | Forward
------------|---------------|----------|-------------|------------------------
Buildserver | 10.211.55.200 | ci-node  | Jenkins     |http://localhost:10000/
Buildserver | 10.211.55.200 | ci-node  | Sonar       |http://localhost:9000/

###Development

#### Git

- A good place to learn about setting up git is [here](https://help.github.com/articles/set-up-git)
- Git [home](http://git-scm.com/) (download, documentation)

## Workings of the application



### Installing dependencies



## Application Directory Layout

    microservice/       --> all of the files to be used in production
      frontend/         --> css files
      service/          --> css files
    monolithic/         --> all of the files to be used in production
      common/           --> css files
      repository/       --> css files
      service/          --> css files
      ui/               --> css files


## Contact


[Bernd Zuther](mailto:bernd.zuther@me.com)

