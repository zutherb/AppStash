package io.github.appstash.shop.service.product

import reactivemongo.api.MongoDriver
import sprest.reactivemongo.ReactiveMongoPersistence

import scala.concurrent.ExecutionContext.Implicits.global

trait Mongo extends ReactiveMongoPersistence {

  protected val driver = new MongoDriver(Boot.system)
  protected val connection = driver.connection(List("localhost"))
  protected val db = connection("pizza")
}

