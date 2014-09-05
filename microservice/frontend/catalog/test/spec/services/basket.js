'use strict';

describe('Service: basket', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var basket;
  beforeEach(inject(function (_basket_) {
    basket = _basket_;
  }));

  it('should do something', function () {
    expect(!!basket).toBe(true);
  });

});
