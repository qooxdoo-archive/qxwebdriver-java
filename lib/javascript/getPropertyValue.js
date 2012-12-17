var getPropertyValue = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  return widget.get(arguments[1]);
};
