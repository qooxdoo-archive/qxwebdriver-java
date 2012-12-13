var getElementFromProperty = function() {
  var widget = qx.core.ObjectRegistry.fromHashCode('%1$s');
  var propVal = widget.get('%2$s');
  return propVal.getContentElement().getDomElement();
};
