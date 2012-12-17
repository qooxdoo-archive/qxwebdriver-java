package org.oneandone.qxwebdriver.widget;

public interface Scrollable {

	public void scrollTo(String direction, Integer position);
	
	public Widget scrollToChild(String direction, org.oneandone.qxwebdriver.By locator);
	
}
