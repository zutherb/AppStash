interface IAlertItem {
    type: string;
    message: string;
}


interface IAlertService {
    add(item:IAlertItem);
    getAll():IAlertItem[];
    clearAlert(index: number);
}

class AlertService implements IAlertService {

    private alerts : IAlertItem[]

    static $inject = [];

    constructor() {
        this.alerts = [];
    }

    add(item:IAlertItem) {
        this.alerts.push(item);
    }

    getAll():IAlertItem[] {
        return this.alerts;
    }

    clearAlert(index: number) {
        this.alerts.slice(index, index+1);
    }
}

eshop.service('alertService', AlertService);