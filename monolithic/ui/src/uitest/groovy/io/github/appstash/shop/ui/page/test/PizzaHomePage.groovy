package io.github.appstash.shop.ui.page.test

import geb.Page

class PizzaHomePage extends Page {
    static url = "http://localhost:8888/pizza/"
    static at = { title == "Pizza Shop" }
    static content = {
        results(wait: true) { $("li.active") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        pizzaCatalogLink { resultLink(0) }
    }
}