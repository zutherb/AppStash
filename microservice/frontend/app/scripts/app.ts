/// <reference path="types.ts"/>

var eshop = angular.module('eshop', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
])
    .config(function ($routeProvider:ng.route.IRouteProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/main.html',
                controller: 'ProductService'
            })
            .when('/productcatalog/:productType', {
                templateUrl: 'views/catalog.html',
                controller: 'ProductService'
            })
            .when('/about', {
                templateUrl: 'views/about.html',
                controller: 'AboutCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    });

