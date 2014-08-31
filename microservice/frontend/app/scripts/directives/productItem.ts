eshop.directive("shopProductItem", function(){
    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/productItem.html";
    directive.replace = true;
    directive.scope = {
        product: '=product'
    }
    console.log(directive)
    return directive;
});





