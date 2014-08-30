/// <reference path="../services/navigation.ts"/>

interface INavigationScope extends ng.IScope {
    items: INavigationItem[];
}

class NavigationController {
    static $inject = ['$scope', 'navigationService'];

    constructor(private $scope:INavigationScope, private navigationService:INavigationService) {
        $scope.items = navigationService.getNavigation();
    }
}

eshop.controller('NavigationController', NavigationController);

