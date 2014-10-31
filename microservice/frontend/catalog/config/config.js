angular.module('eshop.config', [])
  .constant('configuration', {
        "NAVIGATION_SERVICE_URL": "@@NAVIGATION_SERVICE_URL",
        "PRODUCT_SERVICE_URL": "@@PRODUCT_SERVICE_URL",
        "CART_SERVICE_IMPL": "@@CART_SERVICE_IMPL",
        "CART_SERVICE_GET_URL": "@@CART_SERVICE_GET_URL"
    });

