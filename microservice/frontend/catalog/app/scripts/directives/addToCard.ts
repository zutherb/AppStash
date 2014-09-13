interface IShopAddToCardScope extends ng.IScope {
    product :IProduct
    add(product :IProduct);
}


eshop.directive("shopAddToCard", ($rootScope): ng.IDirective => {
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/addtocard.html";
    directive.replace = true;

    directive.scope = {
        product: "=product"
    };

    directive.link = (scope: IShopAddToCardScope) => {
        scope.add = (product :IProduct) => {
            $rootScope.$broadcast(Events.ADD_TO_CARD, product);
        }
    };

    return directive;
});
