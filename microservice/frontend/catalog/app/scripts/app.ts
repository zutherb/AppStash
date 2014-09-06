/// <reference path="types.ts"/>

var _:UnderscoreStatic;
var $:JQueryStatic;

var config_module = angular.module('eshop.config', [])
$.getJSON("/config/config.json").then((data) =>
    angular.forEach(data, (key,value) => config_module.constant(value,key))
);

var eshop = angular.module('eshop', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'eshop.config',
    'ui.bootstrap',
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




