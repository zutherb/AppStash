class LocalStorageCartService extends AbstractCartService implements ICartService {

    private CART_ITEMS_KEY = 'cartItems';
    private qService: ng.IQService;

    static $inject = ['localStorageService', '$q'];

    constructor(private localStorageService: ng.localStorage.ILocalStorageService,
                private $q: ng.IQService) {
        super();
        this.qService = $q;
    }

    add(product: IProduct) {
        var uuid: string = this.newUUID();
        var cartItem: ICartItem = {uuid: uuid, product : product};

        var cartItems: ICartItem[] = this.getCartItems();
        cartItems.push(cartItem);

        this.localStorageService.set(this.CART_ITEMS_KEY, cartItems)
    }

    remove(uuid: string) {
        var cartItems: ICartItem[] = this.getCartItems();
        cartItems = _.without(cartItems, _.findWhere(cartItems, {uuid: uuid}));

        this.localStorageService.set(this.CART_ITEMS_KEY, cartItems)
    }

    getAll(): ng.IPromise <ICartItem[]> {
        var deferred = this.qService.defer();
         deferred.resolve(this.getCartItems());
        return deferred.promise;
    }

    getCartItems(): ICartItem[] {
        return this.localStorageService.get(this.CART_ITEMS_KEY) || []
    }
}

eshop.service('localStorageCartService', LocalStorageCartService);