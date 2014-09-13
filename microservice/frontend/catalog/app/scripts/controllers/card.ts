interface ICardScope extends ng.IScope {
    vm: CardController;
}

class CardController {
    private cardItems: ICardItem[] = [];

    static $inject = ['$scope', '$rootScope', 'cardService'];

    constructor(private $scope: ICardScope, private $rootScope: ng.IScope, private cardService: CardService) {
        this.cardItems = this.cardService.getAll();

        $scope.vm = this;

        $scope.$on(Events.ADD_TO_CARD, (event: ng.IAngularEvent, product: IProduct) => {
            this.add(product);
            this.cardItems = this.cardService.getAll();
        });

        $scope.$on(Events.REMOVE_FROM_CARD, (event: ng.IAngularEvent, uuid: string) => {
            this.remove(uuid);
            this.cardItems = this.cardService.getAll();
        });
    }

    add(product: IProduct) {
        this.cardService.add(product);

    }

    remove(uuid: string) {
        this.cardService.remove(uuid);
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

