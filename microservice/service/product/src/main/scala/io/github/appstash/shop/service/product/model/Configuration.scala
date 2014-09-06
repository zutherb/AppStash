package io.github.appstash.shop.service.product.model

case class SystemConfiguration(val httpHost: String,
                         val httpPort: Int,
                         val databaseHost: String,
                         val databaseName: String,
                         val collectionName: String)