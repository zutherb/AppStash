interface IBasketScope extends ng.IScope{
    vm: BasketController;
}

class BasketController {
    private basketItems: IBasketItem[] = [];

    static $inject = ['$scope', 'basketService'];

    constructor(private $scope: IBasketScope, private basketService: BasketService) {
        $scope.vm = this;
    }

    add(product: IProduct) {
        this.basketItems.push(this.basketService.add(product));
    }

    remove(uuid: string) {
        this.basketService.remove(uuid);
    }

    getAll(): IBasketItem[] {
        return this.basketService.getAll();
    }

    getTotalSum(): number {
        var sum:number = 0;
        _.each(this.getAll(), function(elem:IBasketItem){
            sum += elem.product.price;
        });
        return sum;
    }
}

eshop.controller('basketController', BasketController);

