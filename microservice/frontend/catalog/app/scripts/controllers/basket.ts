interface IBasketScope extends ng.IScope{
    basketItems: IBasketItem[];
    vm: BasketController;
    basketVersion: number;
}

class BasketController {
    private basketItems: IBasketItem[];

    static $inject = ['$scope', 'basketService'];

    constructor(private $scope: IBasketScope, private basketService:IBasketService) {
        this.basketItems = this.getAll();

        $scope.vm = this;
        $scope.basketVersion = 0;

        $scope.$watch("basketVersion", (newValue, oldValue) => $scope.basketItems = this.getAll())
    }

    add(product:IProduct) {
        console.log(product)
        this.basketService.add(product);
        this.$scope.basketVersion++;
    }

    remove(uuid:String) {
        console.log(uuid)
        this.basketService.remove(uuid);
        this.$scope.basketVersion++;
    }

    getAll():IBasketItem[] {
        return this.basketService.getAll();
    }

    getTotalSum() {
        var sum:number = 0;
        _.each(this.getAll(), function(elem:IBasketItem){
            sum += elem.product.price;
        });
        return sum;
    }
}

eshop.controller('basketController', BasketController);

