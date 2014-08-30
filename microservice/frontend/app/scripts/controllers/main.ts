class MainCtrl {
    static $inject = ['$scope'];
    constructor($scope){
        $scope.awesomeThings = [
          'HTML5 Boilerplate',
          'AngularJS',
          'Karma'
        ];
    }
}
eshop.controller('MainCtrl', MainCtrl);

