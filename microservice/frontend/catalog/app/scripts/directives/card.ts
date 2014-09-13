eshop.directive("shopCard", () => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/card.html";
    directive.replace = false;

    directive.scope = {};

    return directive;
});
