/// <reference path="../services/navigation.ts"/>

interface INavigationScope extends ng.IScope {
    items: INavigationItem[];
    vm: NavigationController;
}

class NavigationController {
    private items: INavigationItem[];

    static $inject = ['$scope', 'navigationService'];

    constructor(private $scope:INavigationScope, private navigationService:INavigationService) {
        navigationService.getNavigation().then(
            function (data: INavigationItem[]) {
                $scope.items = data;
            }
        );

        $scope.vm = this;
    }
}

eshop.controller('navigationController', NavigationController);

