package io.github.appstash.shop.service.product

import com.typesafe.config.ConfigFactory

/**
 * @author zutherb
 */
trait ApplicationConfiguration {
  val config = ConfigFactory.load()

}
