/// <reference path="../services/navigation.ts"/>

interface INavigationScope extends ng.IScope {
    items: INavigationItem[];
}

class NavigationController {
    static $inject = ['$scope', 'navigationService'];

    constructor(private $scope:INavigationScope, private navigationService:INavigationService) {
        navigationService.getNavigation().then(
            function (data: INavigationItem[]) {
                $scope.items = data;
            }
        );
    }
}

eshop.controller('NavigationController', NavigationController);

