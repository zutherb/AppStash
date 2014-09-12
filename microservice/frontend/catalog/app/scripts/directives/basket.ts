eshop.directive("shopBasket", () => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/basket.html";
    directive.replace = true;

    directive.scope = {};

    return directive;
});
