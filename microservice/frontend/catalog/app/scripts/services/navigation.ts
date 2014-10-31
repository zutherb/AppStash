/// <reference path="../services/alert.ts"/>

interface INavigationService{
    getNavigation(): ng.IPromise <INavigationItem[]>
}

class NavigationService implements INavigationService{
    private httpService: ng.IHttpService;
    private qService: ng.IQService;
    private alertService: AlertService;
    private configuration:IConfiguration;

    static $inject = ['$http', '$q', 'alertService', 'configuration'];

    constructor($http: ng.IHttpService,
                $q: ng.IQService,
                alertService: AlertService,
                configuration:IConfiguration) {
        this.httpService = $http;
        this.qService = $q;
        this.alertService = alertService;
        this.configuration = configuration;
    }

    getNavigation() : ng.IPromise <INavigationItem[]> {
        var deferred = this.qService.defer();
        this.httpService.get(this.configuration.NAVIGATION_SERVICE_URL)
          .success((data :INavigationItem[]) => deferred.resolve(data))
          .error((error:any) => {
                this.alertService.add({type : "danger", message : "Navigation can not be loaded, navigation backend seems to be unreachable."});
                console.error(error);
            });
        return deferred.promise;
    }
}

eshop.service('navigationService', NavigationService);
