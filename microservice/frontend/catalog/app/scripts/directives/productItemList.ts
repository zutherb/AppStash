eshop.directive("shopProductItemList", () => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/productItemList.html";
    directive.replace = false;
    directive.scope = {
        products: '=products',
        headline: '=headline'
    }

    return directive;
});


