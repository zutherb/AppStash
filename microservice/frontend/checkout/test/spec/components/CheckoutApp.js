'use strict';

describe('Main', function () {
  var CheckoutApp, component;

  beforeEach(function () {
    var container = document.createElement('div');
    container.id = 'content';
    document.body.appendChild(container);

    CheckoutApp = require('../../../src/scripts/components/CheckoutApp.jsx');
    component = CheckoutApp();
  });

  it('should create a new instance of CheckoutApp', function () {
    expect(component).toBeDefined();
  });
});
