'use strict';

var appStashServices = angular.module('appStashServices', ['ngResource']);

appStashServices.factory('Navi', ['$resource',
    function ($resource) {
        return $resource('api/navigation/all', {}, {
            query: {method: 'GET', params: {}, isArray: true}
        });
    }]);