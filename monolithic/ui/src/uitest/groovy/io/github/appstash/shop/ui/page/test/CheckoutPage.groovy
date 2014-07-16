package io.github.appstash.shop.ui.page.test

import geb.Page

class CheckoutPage extends Page {
    static url = "http://localhost:8888/pizza/checkout"
    static at = { title == "Pizza Shop" }
    static content = {
        results(wait: true) { $("div.container") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        submitOrderLink { resultLink(0) }
    }
}