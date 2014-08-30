/// <reference path="../services/product.ts"/>

interface IProductScope extends ng.IScope {
    products: IProduct[];
    productType: String;
}

interface ICatalogRouteParams {
    productType: String;
}

class ProductController {
    static $inject = ['$scope', '$routeParams', 'productService'];

    constructor(private $scope:IProductScope, private $routeParams:ICatalogRouteParams, private productService:IProductService) {
        $scope.productType = $routeParams.productType;

        productService.getProducts().then(
            function (data: IProduct[]) {
                $scope.products = data;
            }
        );
    }
}

eshop.controller('productController', ProductController);