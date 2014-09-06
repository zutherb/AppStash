/// <reference path="../services/alert.ts"/>

interface INavigationService{
    getNavigation(): ng.IPromise <INavigationItem[]>
}

class NavigationService implements INavigationService{
    private httpService: ng.IHttpService;
    private qService: ng.IQService;
    private alertService: AlertService;
    private SERVICE_URL:string;

    static $inject = ['$http', '$q', 'alertService','NAVIGATION_SERVICE_URL'];

    constructor($http: ng.IHttpService,
                $q: ng.IQService,
                alertService: AlertService,
                NAVIGATION_SERVICE_URL:string) {
        this.httpService = $http;
        this.qService = $q;
        this.alertService = alertService;
        this.SERVICE_URL = NAVIGATION_SERVICE_URL;
    }

    getNavigation() : ng.IPromise <INavigationItem[]> {
        var deferred = this.qService.defer();
        var failed = false;
        this.httpService.get(this.SERVICE_URL)
          .success((data :INavigationItem[]) => deferred.resolve(data))
          .error((error:any) => {
                this.alertService.add({type : "danger", message : "Navigation can not be loaded, navigation backend seems to be unreachable."});
                console.error(error);
            });
        return deferred.promise;
    }
}

eshop.service('navigationService', NavigationService);
