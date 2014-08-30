/// <reference path="../services/product.ts"/>

interface IProductScope extends ng.IScope {
    products: IProduct[];
}

class ProductController {
    static $inject = ['$scope', 'productService'];

    constructor(private $scope:IProductScope, private productService:IProductService) {
        productService.getProducts().then(
            function (data: IProduct[]) {
                $scope.products = data;
            }
        );
    }
}

eshop.controller('productController', ProductController);

