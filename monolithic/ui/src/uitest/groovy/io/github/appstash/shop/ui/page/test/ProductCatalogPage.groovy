package io.github.appstash.shop.ui.page.test

import geb.Page

class ProductCatalogPage extends Page {
    static at = { title == "Pizza Shop" }
    static content = {
        results(wait: true) { $("div.thumbnails") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        pizzaDetailPageLink { resultLink(0) }
    }
}