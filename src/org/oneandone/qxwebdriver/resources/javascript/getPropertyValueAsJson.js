var getPropertyValueAsJson = function() {
  var json = window.JSON || qx.lang.Json;
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var val = widget.get(arguments[1]);
  return json.stringify(val);
};
