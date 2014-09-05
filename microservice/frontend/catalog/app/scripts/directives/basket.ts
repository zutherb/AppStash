eshop.directive("shopBasket", () => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/basket.html";

    return directive;
});
