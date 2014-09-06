/// <reference path="../services/alert.ts"/>

interface IAlertScope extends ng.IScope {
    vm: AlertController;
}

class AlertController {

    alerts: IAlertItem[];

    static $inject = ['$scope', 'alertService'];

    constructor(private $scope: IAlertScope, private alertService: AlertService) {
        this.alerts = this.getAll();

        $scope.vm = this;
    }

    add(item: IAlertItem) {
        this.alertService.add(item);
    }

    getAll():IAlertItem[] {
        return this.alertService.getAll();
    }

    closeAlert(index: number) {
        this.alertService.clearAlert(index);
    }
}

eshop.controller('alertController', AlertController);

