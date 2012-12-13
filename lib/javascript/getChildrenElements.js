var getChildrenElements = function() {
  var childrenElements = [];
  var widget = qx.core.ObjectRegistry.fromHashCode('%1$s');
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
