interface ICartItem {
    uuid: string;
    product: IProduct;
}


interface ICartService {
    add(product: IProduct);
    remove(uuid: string);
    getAll(): ICartItem[];
}

class CartService implements ICartService {

    private CART_ITEMS_KEY = 'cartItems';

    static $inject = ['localStorageService'];

    constructor(private localStorageService: ng.localStorage.ILocalStorageService) {}

    add(product: IProduct) {
        var uuid: string = this.newUUID();
        var cartItem: ICartItem = {uuid: uuid, product : product};

        var cartItems: ICartItem[] = this.getAll();
        cartItems.push(cartItem);

        this.localStorageService.set(this.CART_ITEMS_KEY, cartItems)
    }

    remove(uuid: string) {
        var cartItems: ICartItem[] = this.getAll();
        cartItems = _.without(cartItems, _.findWhere(cartItems, {uuid: uuid}));

        this.localStorageService.set(this.CART_ITEMS_KEY, cartItems)
    }

    getAll(): ICartItem[] {
        return this.localStorageService.get(this.CART_ITEMS_KEY) || [];
    }

    private newUUID(): string {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    }
}

eshop.service('cartService', CartService);