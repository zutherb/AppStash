/// <reference path="../services/alert.ts"/>

interface IProductService {
    getProducts(): ng.IPromise <IProduct[]>
}

class ProductService implements IProductService {
    private httpService:ng.IHttpService;
    private qService:ng.IQService;
    private alertService: AlertService;
    private SERVICE_URL:string;

    static $inject = ['$http', '$q', 'alertService', 'PRODUCT_SERVICE_URL'];

    constructor($http:ng.IHttpService,
                $q:ng.IQService,
                alertService: AlertService,
                PRODUCT_SERVICE_URL:string) {
        this.httpService = $http;
        this.qService = $q;
        this.alertService = alertService;
        this.SERVICE_URL = PRODUCT_SERVICE_URL;
    }

    getProducts():ng.IPromise <IProduct[]> {
        var deferred = this.qService.defer();
        this.httpService.get(this.SERVICE_URL)
            .success((data) => deferred.resolve(data))
            .error((error:any) => {
                this.alertService.add({type : "danger", message : "Products can nots be loaded, product backend seems to be unreachable."});
                console.error(error);
            });
        return deferred.promise;
    }
}

eshop.service('productService', ProductService);