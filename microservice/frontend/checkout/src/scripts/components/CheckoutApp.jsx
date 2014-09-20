/**
 * @jsx React.DOM
 */

'use strict';

var React = require('react/addons');
var ReactTransitionGroup = React.addons.TransitionGroup;

// Export React so the devtools can find it
(window !== window.top ? window.top : window).React = React;

// CSS
require('../../styles/reset.css');
require('../../styles/main.css');

var imageURL = require('../../images/yeoman.png');

var CheckoutApp = React.createClass({
  render: function() {
    return (
      <div className='main'>
        <ReactTransitionGroup transitionName="fade">
          <h1>Under Construction</h1>
          <a href="http://shop.microservice.io">
            <img src={imageURL} />
          </a>
        </ReactTransitionGroup>
      </div>
    );
  }
});

React.renderComponent(<CheckoutApp />, document.getElementById('content')); // jshint ignore:line

module.exports = CheckoutApp;
