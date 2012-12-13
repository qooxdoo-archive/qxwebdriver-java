var getPropertyValueAsJson = function() {
  var json = window.JSON || qx.lang.Json;
  var obj = qx.core.ObjectRegistry.fromHashCode('%1$s');
  var val = obj.get('%2$s');
  return json.stringify(val);
};
