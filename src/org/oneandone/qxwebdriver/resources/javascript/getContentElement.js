/* The outer function is needed to run this code in a browser.
   It must be stripped for use with JavascriptExecutor.executeScript */
var getContentElement = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  return widget.getContentElement().getDomElement();
};
