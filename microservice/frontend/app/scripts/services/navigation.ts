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
        this.httpService.get("api/navigation/all")
                        .success(function (data :INavigationItem[]){
                deferred.resolve(data)
            })
            .error(function (error:any){
                console.error("Navigation Service Mock was loaded", error);
                deferred.resolve([
                    {
                        'sum': 33,
                        'name': 'Handy',
                        'urlname': 'handy',
                        '_id': 'handy'
                    },
                    {
                        'sum': 1,
                        'name': 'Tablet',
                        'urlname': 'tablet',
                        '_id': 'tablet'
                    }
                ])
            });
        return deferred.promise;
    }
}

eshop.service('navigationService', NavigationService);
