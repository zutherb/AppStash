class Events{
    public static ADD_TO_CART: string = "ADD_TO_CART";
    public static REMOVE_FROM_CARD: string = "REMOVE_FROM_CARD";
}

interface INavigationItem{
    'sum': number;
    'name': string;
    'urlname': string;
    '_id': string;
}

interface IProduct {
    'id' : string;
    'articleId' : string;
    'name' : string;
    'urlname' : string;
    'description': string;
    'productType': string;
    'price' : number;
}

interface ICartItem {
    uuid: string;
    product: IProduct;
}

interface ICart {
    uuid: string;
    cartItems: ICartItem[];
}


interface ICartService {
    add(product: IProduct);
    remove(uuid: string);
    getAll(): ng.IPromise <ICartItem[]>;
}

interface IConfiguration {
    "NAVIGATION_SERVICE_URL": string;
    "PRODUCT_SERVICE_URL": string;
    "CART_SERVICE_IMPL": string;
    "CART_SERVICE_GET_URL":  string;
}

class AbstractCartService {

    newUUID(): string {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    }

    isEmpty(str: string): boolean {
        return (!str || 0 === str.length);
    }

    isBlank(str: string): boolean {
        return (!str || /^\s*$/.test(str));
    }
}