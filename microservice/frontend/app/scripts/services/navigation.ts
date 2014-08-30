interface INavigationService{
    getNavigation():INavigationItem[]
}

class NavigationService implements INavigationService{
    static $inject = ['$http'];

    constructor(private $http:ng.IHttpService) { /* NOOP*/ }

    getNavigation():INavigationItem[] {
        return [
            {
                'sum': 33,
                'name': 'Handy',
                'urlname': 'handy',
                '_id': 'handy'
            },
            {
                'sum': 1,
                'name': 'Tablet',
                'urlname': 'tablet',
                '_id': 'tablet'
            }
        ];
    }
}

eshop.service('navigationService', NavigationService);