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
])
    .config(function ($routeProvider:ng.route.IRouteProvider) {
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
    });


