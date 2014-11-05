package io.github.appstash.shop.service.product.model

import spray.json._

case class SystemConfiguration(val httpHost: String,
                         val httpPort: Int,
                         val databaseHost: String,
                         val databaseName: String,
                         val collectionName: String,
                         val systemVars: Map[String, String],
                         val envVars: Map[String, String])

object SystemConfiguration extends DefaultJsonProtocol {

  implicit val systemConfigurationJsonFormat = jsonFormat7(SystemConfiguration.apply _)

}