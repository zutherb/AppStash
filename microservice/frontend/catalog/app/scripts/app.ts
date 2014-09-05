/// <reference path="types.ts"/>

var _: UnderscoreStatic;
var $: JQueryStatic;

var eshop = angular.module('eshop', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'LocalStorageModule',
])
    .config(($routeProvider:ng.route.IRouteProvider) => {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html'
            })
            .when('/productcatalog/:productType', {
                templateUrl: 'views/catalog.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    }).config(['localStorageServiceProvider', (localStorageServiceProvider) =>{
        localStorageServiceProvider.setPrefix('eshop');
    }]);


