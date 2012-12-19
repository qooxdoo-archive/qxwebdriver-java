var getItemFromSelectables = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var selectables = widget.getSelectables();
  for (var i=0; i<selectables.length; i++) {
    if ((typeof arguments[1] == "number" && i === arguments[1]) ||
        (typeof arguments[1] == "string" && selectables[i].getLabel().match(new RegExp(arguments[1])))) {
      return selectables[i].getContentElement().getDomElement();
    }
  }
  return null;
};
