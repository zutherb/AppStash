eshop.directive("productItem", function(){
    var directive: ng.IDirective = {};

    directive.restrict = "E";
    directive.templateUrl = "/partials/productItem.html";
    directive.replace = true;

    return directive;
});



