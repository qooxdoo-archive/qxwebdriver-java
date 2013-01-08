var getElementFromProperty = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var propVal = widget.get(arguments[1]);
  return propVal.getContentElement().getDomElement();
};
