document.body.addEventListener('keyup', function(e) {
  var isInput = e.target.nodeName === 'INPUT' ||
    e.target.nodeName === 'TEXTAREA';
  if (!isInput && e.keyCode === 66) {
    window.location.href = '../../';
  }
});
