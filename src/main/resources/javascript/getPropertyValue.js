var getPropertyValue = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var result = widget.get(arguments[1]);
  if (qx.data && qx.data.Array && result instanceof qx.data.Array) {
    result = result.toArray();
  }
  if (result instanceof Array) {
    result = result.map(function(item) {
      return item instanceof qx.core.Object ? item.toString() : item;
    });
  }
  if (result instanceof qx.core.Object) {
    result = result.toString();
  }
  return result;
};
