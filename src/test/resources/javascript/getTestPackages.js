var model = qx.core.Init.getApplication().runner.getTestModel();
var packages = testrunner.runner.ModelUtil.getItemsByProperty(model, "type", "package");

var testPackages = [];
packages.forEach(function(pkg) {
  var name = pkg.getFullName();
  var split = name.split(".");
  if (split.length == 2) {
    var children = pkg.getChildren();
    children.forEach(function(child) {
      if (child.getType() == "class") {
        testPackages.push(child.getFullName());
      }
    });
  } else if (split.length === 3) {
    if (name !== "qx.test.ui") {
      testPackages.push(name);
    }
  } else if (split.length === 4) {
    if (name.indexOf("qx.test.ui") === 0) {
      testPackages.push(name);
    }
  }
});

return testPackages;
