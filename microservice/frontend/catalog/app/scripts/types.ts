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

interface IConfiguration {
    "NAVIGATION_SERVICE_URL": string;
    "PRODUCT_SERVICE_URL": string;
}