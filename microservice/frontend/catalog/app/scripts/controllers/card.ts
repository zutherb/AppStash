interface ICardScope extends ng.IScope {
    vm: CardController;
}

class CardController {
    private cardItems: ICardItem[] = [];

    static $inject = ['$scope', 'cardService', '$rootScope'];

    constructor(private $scope: ICardScope, private cardService: CardService, private $rootScope: ng.IScope) {
        this.cardItems = this.cardService.getAll();

        $scope.vm = this;

        $scope.$on(Events.UPDATE_CARD, (event) => {
            this.cardItems = this.cardService.getAll();
        });
    }

    add(product: IProduct) {
        this.cardService.add(product);
        this.$rootScope.$broadcast(Events.UPDATE_CARD);

    }

    remove(uuid: string) {
        this.cardService.remove(uuid);
        this.$rootScope.$broadcast(Events.UPDATE_CARD);
    }

    getAll(): ICardItem[] {
        return this.cardService.getAll();
    }

    getTotalSum(): number {
        var sum:number = 0;
        _.each(this.cardItems, function(elem:ICardItem){
            sum += elem.product.price;
        });
        return sum;
    }
}

eshop.controller('cardController', CardController);

