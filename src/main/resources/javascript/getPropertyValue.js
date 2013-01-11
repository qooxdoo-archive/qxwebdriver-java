var getPropertyValue = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var result = widget.get(arguments[1]);
  if (qx.data && qx.data.Array && result instanceof qx.data.Array) {
    result = result.toArray();
  }
  return result;
};
