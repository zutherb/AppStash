interface ICartScope extends ng.IScope {
    vm: CartController;
}

class CartController {
    private cartItems: ICartItem[] = [];

    static $inject = ['$scope', '$rootScope', 'cartService'];

    constructor(private $scope: ICartScope, private $rootScope: ng.IScope, private cartService: CartService) {
        this.cartItems = this.cartService.getAll();

        $scope.vm = this;

        $scope.$on(Events.ADD_TO_CART, (event: ng.IAngularEvent, product: IProduct) => {
            this.add(product);
            this.cartItems = this.cartService.getAll();
        });

        $scope.$on(Events.REMOVE_FROM_CARD, (event: ng.IAngularEvent, uuid: string) => {
            this.remove(uuid);
            this.cartItems = this.cartService.getAll();
        });
    }

    add(product: IProduct) {
        this.cartService.add(product);

    }

    remove(uuid: string) {
        this.cartService.remove(uuid);
    }

    getAll(): ICartItem[] {
        return this.cartService.getAll();
    }

    getTotalSum(): number {
        var sum:number = 0;
        _.each(this.cartItems, function(elem:ICartItem){
            sum += elem.product.price;
        });
        return sum;
    }
}

eshop.controller('cartController', CartController);

