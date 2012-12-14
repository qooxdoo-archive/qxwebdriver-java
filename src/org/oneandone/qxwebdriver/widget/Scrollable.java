package org.oneandone.qxwebdriver.widget;

import org.openqa.selenium.WebElement;

public interface Scrollable {

	public void scrollTo(String direction, Integer position);
	
	public WebElement scrollToChild(String direction, org.openqa.selenium.By locator);
	
}
