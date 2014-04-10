var splitPackages = arguments[0] || "";
splitPackages = splitPackages.split(",");

var skipPackages = arguments[1] || "";
skipPackages = skipPackages.split(",");

var model = qx.core.Init.getApplication().runner.getTestModel();
var packages = testrunner.runner.ModelUtil.getItemsByProperty(model, "type", "package");

var addPackage = function(name) {
  if (skipPackages.indexOf(name) === -1) {
    testPackages.push(name);
  }
};

var getClassesFromPackage = function(pkg) {
  var children = pkg.getChildren();
  children.forEach(function(child) {
    if (child.getType() == "class") {
      addPackage(child.getFullName());
    }
  });
};

var testPackages = [];
packages.forEach(function(pkg) {
  var name = pkg.getFullName();
  var split = name.split(".");
  if (split.length == 2) {
    getClassesFromPackage(pkg);
  } else if (split.length === 3) {
    if (splitPackages.indexOf(name) !== -1) {
      getClassesFromPackage(pkg);

    } else {
      addPackage(name);
    }
  } else if (split.length === 4) {
    splitPackages.forEach(function(splitPkg) {
      if (name.indexOf(splitPkg) === 0) {
        addPackage(name);
      }
    });
  }
});

return testPackages;
