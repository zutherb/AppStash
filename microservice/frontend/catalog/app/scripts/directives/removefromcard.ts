interface IShopRemoveFromCardScope extends ng.IScope {
    uuid: string
    remove(uuid: string);
}


eshop.directive("shopRemoveFromCard", ($rootScope): ng.IDirective => {
    this.$inject = ['$rootScope'];

    var directive: ng.IDirective = {};

    directive.restrict = "AE";
    directive.templateUrl = "/partials/removefromcard.html";
    directive.replace = true;

    directive.scope = {
        uuid: "=uuid"
    };

    directive.link = (scope: IShopRemoveFromCardScope) => {
        scope.remove = (uuid: string) => {
            $rootScope.$broadcast(Events.REMOVE_FROM_CARD, uuid);
        }
    };

    return directive;
});
