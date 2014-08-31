/// <reference path="../services/product.ts"/>

class RecommendationController {
    static $inject = ['$scope', 'recommendationService'];

    constructor(private $scope:IProductCatalogScope, private recommendationService:IRecommendationService) {
        $scope.products = recommendationService.getProducts(2);
    }
}

eshop.controller('recommendationController', RecommendationController);


