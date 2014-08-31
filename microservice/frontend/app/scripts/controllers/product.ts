/// <reference path="../services/product.ts"/>

interface IProductCatalogScope extends ng.IScope {
    products: IProduct[];
    productType: String;
    headline: String;
}

interface ICatalogRouteParams {
    productType: String;
}

class ProductController {
    static $inject = ['$scope', '$routeParams', 'productService'];

    constructor(private $scope: IProductCatalogScope,
                private $routeParams: ICatalogRouteParams,
                private productService: IProductService) {
        $scope.productType = $routeParams.productType;
        $scope.headline = "Choose your product";

        productService.getProducts().then(
            function (data: IProduct[]) {
                $scope.products = data;
            }
        );
    }
}

eshop.controller('productController', ProductController);
