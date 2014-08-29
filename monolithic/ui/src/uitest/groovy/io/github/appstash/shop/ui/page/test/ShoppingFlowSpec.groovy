package io.github.appstash.shop.ui.page.test
import geb.spock.GebReportingSpec
/*
add vm params: -Dgeb.build.reportsDir="/tmp/geb" -Dgeb.env="firefox"
 */
class ShoppingFlowSpec extends GebReportingSpec {

    def "ordering"() {
        when:
        to HomePage

        and:
        catalogLink.click()

        then:
        at ProductCatalogPage

        and:
        detailPageLink.click()

        then:
        at ProductDetailPage

        and:
        addToBasketLink.click()

        then:
        at ProductDetailPage

        and:
        to CheckoutPage

        then:
        at CheckoutPage

        and:
        submitOrderLink.click()

        then:
        at OrderConfirmationPage
    }

}