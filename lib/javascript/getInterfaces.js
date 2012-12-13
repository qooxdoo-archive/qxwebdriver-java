/* The outer function is needed to run this code in a browser.
   It must be stripped for use with JavascriptExecutor.executeScript */
var getInheritance = function() {
var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
var iFaces = [];
var clazz = widget.constructor;
qx.Class.getInterfaces(clazz).forEach(function(item, i) {
  iFaces.push(/\\[Interface (.*?)\\]/.exec(item.toString())[1]);
});
return iFaces;
};
