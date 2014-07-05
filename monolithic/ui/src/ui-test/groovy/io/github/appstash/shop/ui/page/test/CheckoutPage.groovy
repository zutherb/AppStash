package io.github.appstash.shop.ui.page.test

import geb.Page

class CheckoutPage extends Page {
    static url = "http://localhost:8081/pizza/checkout"
    static at = { title == "Pizza Supermario" }
    static content = {
        results(wait: true) { $("div.main-container") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        submitOrderLink { resultLink(0) }
    }
}