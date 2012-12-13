var getItemFromSelectables = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var selectables = widget.getSelectables();
  for (var i=0; i<selectables.length; i++) {
   if (selectables[i].getLabel() === arguments[1] || i === arguments[1]) {
     return selectables[i].getContentElement().getDomElement();
   }
  }
  return null;
};
