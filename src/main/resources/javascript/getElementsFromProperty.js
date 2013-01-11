var getElementsFromProperty = function() {
  var getDomElement = function(widget) {
    if (widget.getContentElement && widget.getContentElement()) {
      var contentElement = widget.getContentElement();
      if (contentElement.getDomElement && contentElement.getDomElement()) {
        return(contentElement.getDomElement());
      }
    }
    return null;
  };

  var widgets = [];
  var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
  var value = widget.get(arguments[1]);
  var isDataArray = value instanceof qx.data.Array;

  for (var i=0,l=value.length; i<l; i++) {
    var result = getDomElement(value[i]);
    if (result) {
      widgets.push(result);
    }
  }
  return widgets;
};
