package io.github.appstash.shop.service.product

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.typesafe.config.ConfigFactory
import io.github.appstash.shop.service.product.model.{SystemConfiguration}
import io.github.appstash.shop.service.product.rest.ProductServiceActor
import org.apache.logging.log4j.{Logger, core, LogManager}

import spray.can.Http

object Boot extends App {
  private val config = ConfigFactory.load()

  val systemConfig = new SystemConfiguration(
      config.getString("http.host"),
      config.getInt("http.port"),
      config.getString("mongodb.host"),
      config.getString("mongodb.db"),
      config.getString("mongodb.collection"))

//  log.debug(systemConfig)

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[ProductServiceActor], "demo-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = systemConfig.httpHost, port = systemConfig.httpPort)
}