/* The outer function is needed to run this code in a browser.
   It must be stripped for use with JavascriptExecutor.executeScript */
var getInterfaces = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var iFaces = [];
  var clazz = widget.constructor;
  qx.Class.getInterfaces(clazz).forEach(function(item, i) {
    var match = /\[Interface (.*?)\]/.exec(item.toString());
    if (match && match.length > 1) {
      iFaces.push(match[1]);
    }
  });
  return iFaces;
};
