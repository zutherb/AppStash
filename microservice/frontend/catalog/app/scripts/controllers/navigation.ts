/// <reference path="../services/navigation.ts"/>

interface INavigationScope extends ng.IScope {
    vm: NavigationController;
}

class NavigationController {
    items: INavigationItem[] = [];

    static $inject = ['$scope', 'navigationService'];

    constructor(private $scope:INavigationScope, private navigationService:INavigationService) {
        navigationService.getNavigation().then((data: INavigationItem[]) => this.items = data);

        $scope.vm = this;
    }
}

eshop.controller('navigationController', NavigationController);

