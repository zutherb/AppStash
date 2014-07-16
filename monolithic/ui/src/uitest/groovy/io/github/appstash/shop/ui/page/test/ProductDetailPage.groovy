package io.github.appstash.shop.ui.page.test

import geb.Page

class ProductDetailPage extends Page {
    static at = { title == "Pizza Shop" }
    static content = {
        results(wait: true) { $("div.container div.col-md-8") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        addToBasketLink { resultLink(0) }
    }
}