var getElementFromProperty = function() {
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var propVal = widget.get(arguments[1]);

  try {
    return propVal.getContentElement().getDomElement();
  } catch(ex) {
    throw new Error("Couldn't get DOM element from widget " + propVal.toString() + ": " + ex.message);
  }
};
