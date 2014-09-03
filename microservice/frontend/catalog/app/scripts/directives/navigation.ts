eshop.directive("shopNavigation", function(){
    scope: true;

    var directive: ng.IDirective = {};

    directive.restrict = "E";
    directive.templateUrl = "/partials/navigation.html";
    directive.replace = true;

    return directive;
});
