eshop.directive("shopProductItemList", () => {
    scope: true;

    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/productItemList.html";
    directive.replace = true;

    return directive;
});


