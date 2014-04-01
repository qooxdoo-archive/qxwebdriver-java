var model = qx.core.Init.getApplication().runner.getTestModel();
var classes = testrunner.runner.ModelUtil.getItemsByProperty(model, "type", "class");
return classes.map(function(cls) { return cls.getFullName(); });
