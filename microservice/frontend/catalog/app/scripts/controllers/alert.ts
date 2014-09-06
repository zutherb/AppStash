class AlertController {

    alerts: IAlertItem[];

    static $inject = ['$scope', 'alertService'];

    constructor(private $scope, private alertService: AlertService) {
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

