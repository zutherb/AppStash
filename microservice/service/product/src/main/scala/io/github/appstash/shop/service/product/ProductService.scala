package io.github.appstash.shop.service.product

import akka.actor.Actor
import spray.http.MediaTypes._
import spray.routing._

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
        respondWithMediaType(`application/json`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            """[
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "100",
              |		"name" : "Margherita",
              |		"urlname" : "margherita",
              |		"description" : "our famous tomato sauce and genuine mozzarella",
              |		"type" : "PIZZA",
              |		"category" : "vegetarian",
              |		"price" : 3.8
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "101",
              |		"name" : "Funghi",
              |		"urlname" : "funghi",
              |		"description" : "fresh mushrooms give it that special aroma",
              |		"type" : "PIZZA",
              |		"category" : "vegan",
              |		"price" : 4.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "102",
              |		"name" : "Pepperoni",
              |		"urlname" : "pepperoni",
              |		"description" : "our special salami, hot pepperoni and black olives",
              |		"type" : "PIZZA",
              |		"category" : "carnivore",
              |		"price" : 4.9
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "103",
              |		"name" : "Milano",
              |		"urlname" : "milano",
              |		"description" : "milan pepperoni, fresh tomato, genuine mozzarella and rocket",
              |		"type" : "PIZZA",
              |		"category" : "carnivore",
              |		"price" : 6.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "104",
              |		"name" : "Capricciosa",
              |		"urlname" : "capricciosa",
              |		"description" : "pepperoni, fresh mushrooms, ham, kalamata style olives and genuine mozzarella",
              |		"type" : "PIZZA",
              |		"category" : "carnivore",
              |		"price" : 5.7
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "105",
              |		"name" : "Vegetaria",
              |		"urlname" : "vegetaria",
              |		"description" : "light and sunny: fresh mushrooms, colorful peppers mix and fresh tomato slices",
              |		"type" : "PIZZA",
              |		"category" : "vegetarian",
              |		"price" : 4.9
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "106",
              |		"name" : "Tuna",
              |		"urlname" : "tuna",
              |		"description" : "the clear choice: tuna with red onions and our famous tomato sauce",
              |		"type" : "PIZZA",
              |		"category" : "fish",
              |		"price" : 5.7
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "107",
              |		"name" : "Cheese",
              |		"urlname" : "cheese",
              |		"description" : "for the ultimate taste experience: feta-style cheese, cheddar, gouda and mozzarella",
              |		"type" : "PIZZA",
              |		"category" : "vegetarian",
              |		"price" : 6.9
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "200",
              |		"name" : "Cheese Ravioli",
              |		"urlname" : "cheese-ravioli",
              |		"description" : "red sauce, ravioli and mozzarella cheese",
              |		"type" : "PASTA",
              |		"category" : "vegetarian",
              |		"price" : 4.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "201",
              |		"name" : "Fresh Fettuccine",
              |		"urlname" : "fresh-fettuccine",
              |		"description" : "alfredo sauce, fettuccine, roasted chicken, roasted red pepper, fresh garlic and mozzarella",
              |		"type" : "PASTA",
              |		"category" : "carnivore",
              |		"price" : 5.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "202",
              |		"name" : "Rigatoni Caprese",
              |		"urlname" : "rigatoni-caprese",
              |		"description" : "diced tomatoes, olive oil, garlic, fresh basil and melted mozzarella",
              |		"type" : "PASTA",
              |		"category" : "vegetarian",
              |		"price" : 5.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "203",
              |		"name" : "Lasagne",
              |		"urlname" : "lasagne",
              |		"description" : "served with marinara sauce",
              |		"type" : "PASTA",
              |		"category" : "carnivore",
              |		"price" : 6
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "204",
              |		"name" : "Spaghetti with Clams",
              |		"urlname" : "spaghetti-with-clams",
              |		"description" : "clam sauce with garlic and fresh clam broth",
              |		"type" : "PASTA",
              |		"category" : "fish",
              |		"price" : 6.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "205",
              |		"name" : "Penne Vodka",
              |		"urlname" : "penne-vodka",
              |		"description" : "pink sauce with a touch of vodka, prosciutto and onions",
              |		"type" : "PASTA",
              |		"category" : "carnivore",
              |		"price" : 7
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "206",
              |		"name" : "Farfalle Campanella",
              |		"urlname" : "farfalle-campanella",
              |		"description" : "bow tie shaped pasta with fresh tomatoes, eggplant topped with basil and diced mozzarella",
              |		"type" : "PASTA",
              |		"category" : "vegetarian",
              |		"price" : 6
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "207",
              |		"name" : "Shrimp Spaghetti",
              |		"urlname" : "shrimp-spaghetti",
              |		"description" : "battered shrimp with lemon white wine sauce over spaghetti",
              |		"type" : "PASTA",
              |		"category" : "fish",
              |		"price" : 6.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "300",
              |		"name" : "Caesar Salad",
              |		"urlname" : "caesar-salad",
              |		"description" : "crisp romaine topped with creamy caesar dressing, parmesan and croutons",
              |		"type" : "SALAD",
              |		"category" : "vegetarian",
              |		"price" : 5.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "301",
              |		"name" : "Chef Salad",
              |		"urlname" : "chef-salad",
              |		"description" : "crisp romaine, thinly sliced ham and turkey, pepper jack, shredded carrots, tomatoe slices, olives, sunflower seeds topped with sesame oriental dressing",
              |		"type" : "SALAD",
              |		"category" : "carnivore",
              |		"price" : 6.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "302",
              |		"name" : "House Salad",
              |		"urlname" : "house-salad",
              |		"description" : "crisp romaine, pepper jack, olives, shredded carrots, tomatoe slices and sunflower seeds",
              |		"type" : "SALAD",
              |		"category" : "vegan",
              |		"price" : 5.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "303",
              |		"name" : "Bangkok Thai Salad",
              |		"urlname" : "bangkok-thai-salad",
              |		"description" : "crisp romaine, shredded carrots, grilled chicken breast, crunch noodles, water chestnuts, sunflower seeds topped with sesame oriental dressing",
              |		"type" : "SALAD",
              |		"category" : "carnivore",
              |		"price" : 6.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "304",
              |		"name" : "Apple-Walnut Tuna Salad",
              |		"urlname" : "apple-walnut-tuna-salad",
              |		"description" : "imported Italian light tuna, apple slices, walnuts, crisp romaine, shredded carrots, tomatoe slices, black olives, sunflower seeds and croutons",
              |		"type" : "SALAD",
              |		"category" : "fish",
              |		"price" : 6.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "305",
              |		"name" : "Strawberry Shrimp Salad",
              |		"urlname" : "strawberry-shrimp-salad",
              |		"description" : "Grilled shrimps, sliced strawberries, cubed fresh cottage cheese, green onions, chopped romaine heart, shredded carrots, sundried tomatoes, and toasted pecans tossed with balsamic-honey mustard dressing",
              |		"type" : "SALAD",
              |		"category" : "fish",
              |		"price" : 6.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "306",
              |		"name" : "All Seasons Salad",
              |		"urlname" : "all-seasons-salad",
              |		"description" : "crisp romaine tossed with artichoke hearts, tomato, olives, grilled red onions, toasted pine nuts and balsamic vinaigrette, topped with crumbled feta cheese and served with bread and butter",
              |		"type" : "SALAD",
              |		"category" : "vegetarian",
              |		"price" : 6
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "307",
              |		"name" : "Apple Roast Beef Salad",
              |		"urlname" : "apple-roast-beef-salad",
              |		"description" : "thinly sliced roast beef, spring mix, toasted almond slices, apple slices, mandarin orange, and shredded carrots tossed with sesame soy blended with Italian dressing",
              |		"type" : "SALAD",
              |		"category" : "carnivore",
              |		"price" : 6.4
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "400",
              |		"name" : "Coca Cola 0.33l",
              |		"urlname" : "coca-cola-0-33l",
              |		"description" : "The authentic cola sensation that is a refreshing part of sharing life's enjoyable moments.",
              |		"type" : "BEVERAGE",
              |		"category" : "soda",
              |		"price" : 1.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "401",
              |		"name" : "Fanta Mango 0.33l",
              |		"urlname" : "fanta-mango-0-33l",
              |		"description" : "Exuberant tropical fun to release you from the everyday mundane.",
              |		"type" : "BEVERAGE",
              |		"category" : "soda",
              |		"price" : 1.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "402",
              |		"name" : "Sprite Zero 0.33l",
              |		"urlname" : "sprite-zero-0-33l",
              |		"description" : "Unique Lymon (lemon-lime) flavor, clear, clean and crisp without caffeine.",
              |		"type" : "BEVERAGE",
              |		"category" : "soda",
              |		"price" : 1.5
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "403",
              |		"name" : "Augustiner Hell 0.5l",
              |		"urlname" : "augustiner-hell-0-5l",
              |		"description" : "A particularily mild, sparkling, long stored beer, refreshing and easily digestible at the same time. Ingredients: water, barley malt, hops, ALC. 5.2% vol.",
              |		"type" : "BEVERAGE",
              |		"category" : "beer",
              |		"price" : 2.1
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "404",
              |		"name" : "Tegern Seer Hell 0.5l",
              |		"urlname" : "tegern-seer-hell-0-5l",
              |		"description" : "This slightly dry and lean pale lager beer has a clean and crisp taste.",
              |		"type" : "BEVERAGE",
              |		"category" : "beer",
              |		"price" : 2.1
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "405",
              |		"name" : "Orange Juice 0.33l",
              |		"urlname" : "orange-juice-0-33l",
              |		"description" : "Freshly sqeezed orange juice without preservative.",
              |		"type" : "BEVERAGE",
              |		"category" : "juice",
              |		"price" : 2.8
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "406",
              |		"name" : "Apple Juice 0.33l",
              |		"urlname" : "apple-juice-0-33l",
              |		"description" : "Freshly sqeezed apple juice without preservative.",
              |		"type" : "BEVERAGE",
              |		"category" : "juice",
              |		"price" : 2.8
              |	},
              |	{
              |		"_class" : "com.comsysto.shop.repository.product.model.Product",
              |		"articleId" : "407",
              |		"name" : "Evian 0.5l",
              |		"urlname" : "evian-0-5l",
              |		"description" : "Fresh, crisp tasting water.",
              |		"type" : "BEVERAGE",
              |		"category" : "water",
              |		"price" : 1
              |	}
              |]""" stripMargin
          }
        }
      }
    }
}