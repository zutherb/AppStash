'use strict';

describe('Controller: BasketCtrl', function () {

  // load the controller's module
  beforeEach(module('clientApp'));

  var BasketCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    BasketCtrl = $controller('BasketCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
