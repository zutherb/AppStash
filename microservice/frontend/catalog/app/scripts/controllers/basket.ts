interface IBasketScope extends ng.IScope{
    vm: BasketController;
}

class BasketController {
    private basketItems: IBasketItem[] = [];

    static $inject = ['$scope', 'basketService'];

    constructor(private $scope: IBasketScope, private basketService: BasketService) {
        this.basketItems = this.basketService.getAll();

        $scope.vm = this;

        $scope.$on(Events.UPDATE_BASKET, (event) => {
            this.basketItems = this.basketService.getAll();
        });
    }

    add(product: IProduct) {
        this.basketService.add(product);
        this.$scope.$emit(Events.UPDATE_BASKET);
    }

    remove(uuid: string) {
        this.basketService.remove(uuid);
        this.$scope.$emit(Events.UPDATE_BASKET);
    }

    private getAll(): IBasketItem[] {
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

