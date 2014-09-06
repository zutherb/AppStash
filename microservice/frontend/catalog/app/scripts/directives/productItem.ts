eshop.directive("shopProductItem", ()=>{
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/productItem.html";
    directive.replace = false;
    directive.scope = {
        product: '=product'
    }

    return directive;
});





