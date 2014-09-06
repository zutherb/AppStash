/// <reference path="types.ts"/>

var _:UnderscoreStatic;
var $:JQueryStatic;

var eshop = angular.module('eshop', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'eshop.config',
    'ui.bootstrap',
    'ui.bootstrap.tpls',
    'ui.bootstrap.transition',
    'LocalStorageModule'
]).config(($routeProvider:ng.route.IRouteProvider) => {
    $routeProvider
        .when('/', {
            templateUrl: 'views/main.html'
        })
        .when('/productcatalog/:productType', {
            templateUrl: 'views/catalog.html'
        });
}).config(['localStorageServiceProvider', (localStorageServiceProvider) => {
    localStorageServiceProvider.setPrefix('eshop');
}]);



