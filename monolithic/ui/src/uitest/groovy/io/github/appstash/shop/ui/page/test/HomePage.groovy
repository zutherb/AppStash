package io.github.appstash.shop.ui.page.test

import geb.Page

class HomePage extends Page {
    static url = "http://localhost:8888/shop/"
    static at = { title == "Shop" }
    static content = {
        results(wait: true) { $("li.active") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        catalogLink { resultLink(0) }
    }
}