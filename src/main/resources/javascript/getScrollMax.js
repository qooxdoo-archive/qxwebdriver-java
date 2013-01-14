var getScrollMax = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var methodName = "getScrollMax" + arguments[1].toUpperCase();
  return widget[methodName]();
};
