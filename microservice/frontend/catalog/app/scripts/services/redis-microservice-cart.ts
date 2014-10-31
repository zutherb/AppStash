class RedisMircoserviceCartService extends AbstractCartService implements ICartService {


    private CART_ID = 'cartId';

    private qService: ng.IQService;
    private httpService: ng.IHttpService;

    static $inject = ['localStorageService', '$http', '$q', 'configuration'];

    constructor(private localStorageService: ng.localStorage.ILocalStorageService,
                private $http: ng.IHttpService,
                private $q: ng.IQService,
                private configuration:IConfiguration) {
        super();
        this.httpService = $http;
        this.qService = $q;
    }

    add(product: IProduct) {
        var uuid: string = this.newUUID();
        var cartItem: ICartItem = {uuid: uuid, product : product};

        if(!this.hasCardId()) {
            this.httpService.put(this.configuration.CART_SERVICE_PUT_URL, cartItem)
                .success((cartId: string) => {
                    this.localStorageService.set(this.CART_ID, cartId.replace(/\"/g, ""));
                })
                .error((error) => console.log(error));
        }else {
            this.httpService.post(this.configuration.CART_SERVICE_POST_URL, cartItem, {params : {cartId: this.getCartId()}})
                .error((error) => console.log(error));
        }
    }

    remove(itemId: string) {
        if(this.hasCardId()){
            this.httpService.delete(this.configuration.CART_SERVICE_DELETE_URL, {params : {cartId: this.getCartId(), itemId: itemId}})
                .error((error) => console.log(error));
        }
    }

    getAll(): ng.IPromise<ICartItem[]> {
        var deferred = this.qService.defer();

        if(this.hasCardId()){
            this.httpService.get(this.configuration.CART_SERVICE_GET_URL + this.getCartId())
                .success((data: ICart) => deferred.resolve(data.cartItems))
                .error((error:any) => console.error(error));
        }else{
            deferred.resolve([]);
        }

        return deferred.promise;
    }

    hasCardId(): boolean {
        var cardId: string = this.getCartId();
        return !this.isEmpty(cardId) && !this.isBlank(cardId);
    }

    getCartId(): string {
        return this.localStorageService.get(this.CART_ID);
    }

}

eshop.service('redisMircoserviceCartService', RedisMircoserviceCartService);