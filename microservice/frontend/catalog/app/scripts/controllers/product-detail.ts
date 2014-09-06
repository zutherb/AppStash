/// <reference path="../services/product.ts"/>

class ProductDetailController {
    product: IProduct;

    static $inject = ['$scope', '$routeParams', 'productService'];

    constructor(private $scope,
                private $routeParams,
                private productService: IProductService,
                private alertService: IAlertService
        ) {

        productService.getProducts().then((data: IProduct[]) =>  {
            var products = _.filter(data, (item: IProduct) => item.urlname == $routeParams.urlname)
            if(!_.isEmpty(products)){
                this.product = products[0];
            }else{
                alertService.add({type : "danger", message : "Could not find Product"})
            }
        });

        $scope.vm = this;
    }
}

eshop.controller('productDetailController', ProductDetailController);
