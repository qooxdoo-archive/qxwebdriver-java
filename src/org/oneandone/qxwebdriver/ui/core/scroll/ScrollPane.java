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
		String methodName = "scrollTo" + direction.toUpperCase();
		String script = "qx.ui.core.Widget.getWidgetByElement(arguments[0])." +
		methodName + "(" + Integer.toString(position) + ")";
		executeJavascript(script);
	}
	
	public Long getMaximum(String direction) {
		String methodName = "getScrollMax" + direction.toUpperCase();
		String script = "return qx.ui.core.Widget.getWidgetByElement(arguments[0])." +
		methodName + "();";
		return (Long) executeJavascript(script);
	}
	
	public Long getScrollPosition(String direction) {
		String propertyName = "scroll" + direction.toUpperCase();
		return (Long) getPropertyValue(propertyName);
	}
	
	public Long getScrollStep(String direction) {
		return (long) 10;
	}
}
