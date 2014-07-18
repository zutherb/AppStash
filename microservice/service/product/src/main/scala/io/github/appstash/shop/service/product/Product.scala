package io.github.appstash.shop.service.product

import sprest.models.{Model, ModelCompanion}

/**
 * @author zutherb
 */
case class Product(var id: Option[String] = None,
                   name: String) extends Model[String]

object Product extends ModelCompanion[Product, String] {

  implicit val productJsonFormat = jsonFormat2(Product.apply _)
}
