package io.github.appstash.shop.service.product

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}
import spray.json._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author zutherb
 *
 */
object ProductType extends Enumeration {
  type productType = Value
  val PIZZA, PASTA, SALAD, BEVERAGE = Value
}


case class Product(var id: String,
                   articleId: String,
                   name: String,
                   urlname: String,
                   description: String,
                   productType: String,
                   price: Double)

object Product extends DefaultJsonProtocol {

  implicit val productJsonFormat = jsonFormat7(Product.apply _)

  implicit object PersonReader extends BSONDocumentReader[Product] {
    def read(doc: BSONDocument): Product = {
      val id = doc.getAs[BSONObjectID]("_id").get
      val articleId = doc.getAs[String]("articleId").get
      val name = doc.getAs[String]("name").get
      val urlname = doc.getAs[String]("urlname").get
      val description = doc.getAs[String]("description").get
      val productType = doc.getAs[String]("type").get
      val price = doc.getAs[Double]("price").get

      Product(id.stringify, articleId, name, urlname, description, productType, price)
    }
  }

}

case class ProductQuery(id: Option[String],
                        productType: Option[String],
                        articleId: Option[String],
                        name: Option[String],
                        urlname: Option[String])

object ProductQuery {

  implicit object ProductQueryWriter extends BSONDocumentWriter[ProductQuery] {
    def write(query: ProductQuery): BSONDocument = {
      var document: BSONDocument = BSONDocument()
      if (!query.id.isEmpty && !query.id.get.isEmpty) {
        document = document ++ BSONDocument("_id" -> BSONObjectID(query.id.get))
      }
      if (!query.productType.isEmpty && !query.productType.get.isEmpty) {
        document = document ++ BSONDocument("type" -> query.productType.get)
      }
      if (!query.articleId.isEmpty && !query.articleId.get.isEmpty) {
        document = document ++ BSONDocument("articleId" -> query.articleId.get)
      }
      if (!query.name.isEmpty && !query.name.get.isEmpty) {
        document = document ++ BSONDocument("name" -> query.name.get)
      }
      if (!query.urlname.isEmpty && !query.urlname.get.isEmpty) {
        document = document ++ BSONDocument("urlname" -> query.urlname.get)
      }
      document
    }
  }

}

object Products extends Mongo {
  private val productCollection = db("product")

  def findAll() = productCollection.find(BSONDocument.empty)
    .cursor[Product]
    .collect[List]()

  def findByQuery(query: ProductQuery) = productCollection.find(query)
    .cursor[Product]
    .collect[List]()
}

