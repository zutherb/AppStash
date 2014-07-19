package io.github.appstash.shop.service.product

import akka.actor.Actor
import reactivemongo.api.MongoDriver
import spray.http.MediaTypes
import spray.httpx.SprayJsonSupport.sprayJsonMarshaller
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
import spray.json.pimpAny
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class ProductServiceActor extends Actor with ProductService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}


// this trait defines our service behavior independently from the service actor
trait ProductService extends HttpService {
  val myRoute =
    path("products") {
      get {
        respondWithMediaType(MediaTypes.`application/json`) {
          complete {
            Products.findAll()
          }
        }
      }
    }
}