interface INavigationService{
    getNavigation(): ng.IPromise <INavigationItem[]>
}

class NavigationService implements INavigationService{
    private httpService: ng.IHttpService;
    private qService: ng.IQService;

    static $inject = ['$http', '$q'];

    constructor($http: ng.IHttpService, $q: ng.IQService) {
        this.httpService = $http;
        this.qService = $q;
    }

    getNavigation() : ng.IPromise <INavigationItem[]> {
        var deferred = this.qService.defer();
        var failed = false;
        this.httpService.get("/mock-data/navigation.json")
//        this.httpService.get("api/navigation/all")
          .success((data :INavigationItem[]) => {
                deferred.resolve(data)
            })
          .error((error:any) => {
                console.error("Navigation Service Mock was loaded", error);
            });
        return deferred.promise;
    }
}

eshop.service('navigationService', NavigationService);
