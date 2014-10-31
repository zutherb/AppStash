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



//        this.localStorageService.set(this.CART_ID, cartItems)
    }

    remove(uuid: string) {

//        this.localStorageService.set(this.CART_ID, cartItems)
    }

    getAll(): ng.IPromise<ICartItem[]> {
        var deferred = this.qService.defer();
        var cardId = this.localStorageService.get(this.CART_ID);

        if(!this.isEmpty(cardId) && !this.isBlank(cardId)){
            this.httpService.get(this.configuration.CART_SERVICE_GET_URL)
                .success((data: ICart) => deferred.resolve(data.cartItems))
                .error((error:any) => console.error(error));
        }else{
            deferred.resolve([]);
        }

        return deferred.promise;
    }


}

eshop.service('redisMircoserviceCartService', RedisMircoserviceCartService);