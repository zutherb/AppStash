package io.github.appstash.shop.ui.page.test
import geb.spock.GebReportingSpec
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

/*
add vm params: -Dgeb.build.reportsDir="/tmp/geb" -Dgeb.env="firefox"
 */
class ShoppingFlowSpec extends GebReportingSpec {


//    environments {
//        // when system property 'geb.env' is set to 'win-ie' use a remote IE driver
//        'iphone' {
//            driver = {
//                DesiredCapabilities capabilities = new DesiredCapabilities();
//                capabilities.setCapability("deviceName", "iPhone Simulator");
//                capabilities.setCapability("platformName", "iOS");
//                capabilities.setCapability("platformVersion", "7.1");
//                capabilities.setCapability("browserName", "safari");
//                driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),
//                        capabilities);
//                driver
//            }
//        }
//    }

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
        addToCartLink.click()

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