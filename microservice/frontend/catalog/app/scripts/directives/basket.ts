eshop.directive("shopBasket", () => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/basket.html";
    directive.replace = false;

    directive.scope = {};

    return directive;
});
