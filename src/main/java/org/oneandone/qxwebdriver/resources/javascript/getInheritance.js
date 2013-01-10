/* The outer function is needed to run this code in a browser.
   It must be stripped for use with JavascriptExecutor.executeScript */
var getInheritance = function() {
var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);
var hierarchy = [];
var clazz = widget.constructor;
while (clazz && clazz.classname) {
  hierarchy.push(clazz.classname);
  clazz = clazz.superclass;
}
return hierarchy;
};
