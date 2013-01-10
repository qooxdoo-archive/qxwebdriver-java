var isApplicationReady = function() {
  return (qx && qx.core && qx.core.Init && !!qx.core.Init.getApplication());
};
