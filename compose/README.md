# AppStash - Microservice Phone Shop Application

This example shows how to run the Microservice Phone Shop Application with docker.

![Microservice Appserver](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/microservice-appserver.png)

## Install

If you want to run the example Microservice Phone Shop Application with, you have to install the following components:

```
brew install docker boot2docker docker-compose
boot2docker init
```

## Run

[Compose](https://github.com/docker/compose) is a tool for defining and running complex applications with Docker. With
Compose, you define a multi-container application in a single file, then spin your application up in a single command
which does everything that needs to be done to get it running.

Compose is great for development environments, staging servers, and CI. It isn't recommend to use it in production yet.

![Docker Deployment Diagram](https://raw.githubusercontent.com/zutherb/AppStash/master/external/images/deployment_diagramm_online_shop_docker.png)

```
boot2docker start
docker-compose up
docker-compose -f docker-compose-variant-b.yml up
```