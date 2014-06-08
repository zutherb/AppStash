package com.comsysto.shop.ui.page.test

import geb.Page

class ProductDetailPage extends Page {
    static at = { title == "Pizza Supermario" }
    static content = {
        results(wait: true) { $("div.main-container") }
        result { i -> results[i] }
        resultLink { i -> result(i).find("a")[0] }
        addToBasketLink { resultLink(0) }
    }
}