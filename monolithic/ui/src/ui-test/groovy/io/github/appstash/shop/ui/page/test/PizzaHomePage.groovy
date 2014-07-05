package io.github.appstash.shop.ui.page.test

import geb.Page

class PizzaHomePage extends Page {
    static url = "http://localhost:8888/pizza/"
    static at = { title == "Pizza Supermario" }
    static content = {
        results(wait: true) { $("li.active + li") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        pizzaCatalogLink { resultLink(0) }
    }
}