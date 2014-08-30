'use strict';

angular.module('shopApplication')
  .controller('NaviCtrl', function ($scope, $http) {
    $scope.items = [
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
    $http.get('/api/navigation/all').success(function(data) {
        console.log('info', JSON.stringify(data));
        $scope.items = data;
    });


  });
