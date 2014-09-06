interface INavigationService{
    getNavigation(): ng.IPromise <INavigationItem[]>
}

class NavigationService implements INavigationService{
    private httpService: ng.IHttpService;
    private qService: ng.IQService;
    private SERVICE_URL:string;

    static $inject = ['$http', '$q', 'NAVIGATION_SERVICE_URL'];

    constructor($http: ng.IHttpService, $q: ng.IQService, NAVIGATION_SERVICE_URL:string) {
        this.httpService = $http;
        this.qService = $q;
        this.SERVICE_URL = NAVIGATION_SERVICE_URL;
    }

    getNavigation() : ng.IPromise <INavigationItem[]> {
        var deferred = this.qService.defer();
        var failed = false;
        this.httpService.get(this.SERVICE_URL)
          .success((data :INavigationItem[]) => deferred.resolve(data))
          .error((error:any) => console.error("Navigation Service Mock was loaded", error));
        return deferred.promise;
    }
}

eshop.service('navigationService', NavigationService);
