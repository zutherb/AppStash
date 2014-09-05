interface IProductService {
    getProducts(): ng.IPromise <IProduct[]>
}

class ProductService implements IProductService {
    private httpService:ng.IHttpService;
    private qService:ng.IQService;


    static $inject = ['$http', '$q'];

    constructor($http:ng.IHttpService, $q:ng.IQService) {
        this.httpService = $http;
        this.qService = $q;
    }

    getProducts():ng.IPromise <IProduct[]> {
        var deferred = this.qService.defer();
//        this.httpService.get("api/product/all")
        this.httpService.get("/mock-data/products.json")
            .success((data) => deferred.resolve(data))
            .error((error:any) => console.error("Product Service Mock was loaded", error));
        return deferred.promise;
    }
}

eshop.service('productService', ProductService);