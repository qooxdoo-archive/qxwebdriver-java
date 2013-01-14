package org.oneandone.qxwebdriver.ui.core.scroll;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.openqa.selenium.WebElement;

public class ScrollPane extends AbstractScrollArea implements Scrollable {

	public ScrollPane(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	@Override
	public void scrollTo(String direction, Integer position) {
		jsRunner.runScript("scrollTo", contentElement, position, direction);
	}
	
	public Long getMaximum(String direction) {
		return (Long) jsRunner.runScript("getScrollMax", contentElement, direction);
	}
	
	public Long getScrollPosition(String direction) {
		String propertyName = "scroll" + direction.toUpperCase();
		return (Long) getPropertyValue(propertyName);
	}
	
	public Long getScrollStep(String direction) {
		return (long) 10;
	}
}
