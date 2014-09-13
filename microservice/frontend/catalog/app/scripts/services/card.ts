interface ICardItem {
    uuid: string;
    product: IProduct;
}


interface ICardService {
    add(product: IProduct);
    remove(uuid: string);
    getAll(): ICardItem[];
}

class CardService implements ICardService {

    private CARD_ITEMS_KEY = 'cardItems';

    static $inject = ['localStorageService'];

    constructor(private localStorageService: ng.localStorage.ILocalStorageService) {}

    add(product: IProduct) {
        var uuid: string = this.newUUID();
        var cardItem: ICardItem = {uuid: uuid, product : product};

        var cardItems: ICardItem[] = this.getAll();
        cardItems.push(cardItem);

        this.localStorageService.set(this.CARD_ITEMS_KEY, cardItems)
    }

    remove(uuid: string) {
        var cardItems: ICardItem[] = this.getAll();
        cardItems = _.without(cardItems, _.findWhere(cardItems, {uuid: uuid}));

        this.localStorageService.set(this.CARD_ITEMS_KEY, cardItems)
    }

    getAll(): ICardItem[] {
        return this.localStorageService.get(this.CARD_ITEMS_KEY) || [];
    }

    private newUUID(): string {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    }
}

eshop.service('cardService', CardService);