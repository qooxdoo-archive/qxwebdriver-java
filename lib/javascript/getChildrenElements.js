var getChildrenElements = function() {
  var childrenElements = [];
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  widget.getChildren().forEach(function(child) {
    if (child.getContentElement && child.getContentElement()) {
      var contentElement = child.getContentElement();
      if (contentElement.getDomElement && contentElement.getDomElement()) {
        childrenElements.push(contentElement.getDomElement());
      }
    }
  });
  return childrenElements;
};
