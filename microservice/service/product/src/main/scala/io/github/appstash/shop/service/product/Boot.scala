package io.github.appstash.shop.service.product

import java.net.InetAddress

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import io.github.appstash.shop.service.product.api.{ConfigurationModule, MongoDBModule}
import io.github.appstash.shop.service.product.impl.{ShopProductRepositoryModule, ShopDBModule}
import io.github.appstash.shop.service.product.rest.ProductServiceActor

import spray.can.Http

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[ProductServiceActor], "demo-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 18080)
}