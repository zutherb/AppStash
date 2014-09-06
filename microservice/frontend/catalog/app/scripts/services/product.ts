/// <reference path="../services/alert.ts"/>

interface IProductService {
    getProducts(): ng.IPromise <IProduct[]>
}

class ProductService implements IProductService {
    private httpService: ng.IHttpService;
    private qService: ng.IQService;
    private alertService: AlertService;
    private configuration: IConfiguration;

    static $inject = ['$http', '$q', 'alertService', 'configuration'];

    constructor($http:ng.IHttpService,
                $q:ng.IQService,
                alertService: AlertService,
                configuration:IConfiguration) {
        this.httpService = $http;
        this.qService = $q;
        this.alertService = alertService;
        this.configuration = configuration;
    }

    getProducts():ng.IPromise <IProduct[]> {
        var deferred = this.qService.defer();
        this.httpService.get(this.configuration.PRODUCT_SERVICE_URL)
            .success((data) => deferred.resolve(data))
            .error((error:any) => {
                this.alertService.add({type : "danger", message : "Products can nots be loaded, product backend seems to be unreachable."});
                console.error(error);
            });
        return deferred.promise;
    }
}

eshop.service('productService', ProductService);