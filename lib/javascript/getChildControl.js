var getChildControl = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  return widget.getChildControl(arguments[1]).getContentElement().getDomElement();
};
