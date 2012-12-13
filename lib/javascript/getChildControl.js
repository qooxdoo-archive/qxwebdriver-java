var getChildControl = function() {
  var widget = qx.core.ObjectRegistry.fromHashCode('%1$s');
  return widget.getChildControl('%2$s').getContentElement().getDomElement();
};
/*
Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getChildControl"),
        contentElement, childControlId);
*/
