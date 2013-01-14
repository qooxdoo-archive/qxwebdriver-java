var scrollTo = function() {
  var methodName = "scrollTo";
  if (arguments[2]) {
    methodName += arguments[2].toUpperCase();
  }
  qx.ui.core.Widget.getWidgetByElement(arguments[0])[methodName](arguments[1]);
};
