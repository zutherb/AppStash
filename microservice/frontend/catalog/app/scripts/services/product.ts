interface IProductService {
    getProducts(): ng.IPromise <IProduct[]>
}

class ProductService implements IProductService {
    private httpService:ng.IHttpService;
    private qService:ng.IQService;
    private SERVICE_URL:string;

    static $inject = ['$http', '$q', 'PRODUCT_SERVICE_URL'];

    constructor($http:ng.IHttpService, $q:ng.IQService, PRODUCT_SERVICE_URL:string) {
        this.httpService = $http;
        this.qService = $q;
        this.SERVICE_URL = PRODUCT_SERVICE_URL;
    }

    getProducts():ng.IPromise <IProduct[]> {
        var deferred = this.qService.defer();
        this.httpService.get(this.SERVICE_URL)
            .success((data) => deferred.resolve(data))
            .error((error:any) => console.error("Product Service Mock was loaded", error));
        return deferred.promise;
    }
}

eshop.service('productService', ProductService);