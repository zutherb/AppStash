// <reference path="../services/cart.ts"/>

interface ICartScope extends ng.IScope {
    vm: CartController;
}

class CartController {
    private cartItems: ICartItem[] = [];

    static $inject = ['$scope', '$rootScope', 'cartServiceResolver'];

    constructor(private $scope: ICartScope,
                private $rootScope: ng.IScope,
                private cartService: ICartService) {
        this.cartService.getAll().then((data: ICartItem[]) => this.cartItems = data );

        $scope.vm = this;

        $scope.$on(Events.ADD_TO_CART, (event: ng.IAngularEvent, product: IProduct) => {
            this.add(product);
            this.cartService.getAll().then((data: ICartItem[]) => this.cartItems = data );
        });

        $scope.$on(Events.REMOVE_FROM_CARD, (event: ng.IAngularEvent, uuid: string) => {
            this.remove(uuid);
            this.cartService.getAll().then((data: ICartItem[]) => this.cartItems = data );
        });
    }

    add(product: IProduct) {
        this.cartService.add(product);

    }

    remove(uuid: string) {
        this.cartService.remove(uuid);
    }

    getAll(): ng.IPromise<ICartItem[]> {
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

