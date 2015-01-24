interface IAlertScope extends ng.IScope {
    vm: ShopAlertController;
}

class ShopAlertController {

    alerts:IAlertItem[] = [];

    static $inject = ['$scope', '$rootScope'];

    constructor(private $scope: IAlertScope,
                private $rootScope: ng.IScope) {

        $rootScope.$on(Eventnames.ADD_ALERT_MESSAGE, (event:ng.IAngularEvent, alert:IAlertItem) => {
            this.alerts.push(alert);
        });

        $scope.vm = this;
    }

    getAll():IAlertItem[] {
        return this.alerts;
    }

    closeAlert(index:number) {
        this.alerts.slice(index, 1);
    }
}

eshop.controller('shopAlertController', ShopAlertController);

