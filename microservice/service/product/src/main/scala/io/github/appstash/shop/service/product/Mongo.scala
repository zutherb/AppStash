package io.github.appstash.shop.service.product

import reactivemongo.api.MongoDriver

import scala.concurrent.ExecutionContext.Implicits.global

trait Mongo {
  protected val driver = new MongoDriver(Boot.system)
  protected val connection = driver.connection(List("localhost"))
  protected val db = connection("shop")
}

