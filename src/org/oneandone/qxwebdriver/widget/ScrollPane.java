package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

public class ScrollPane extends ScrollArea implements Scrollable {

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

	//@Override
	/*
	public Widget scrollToChild(String direction, By locator) {
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		
		Long singleStep = getScrollStep(direction);
		Long maximum = getMaximum(direction);
		Long scrollPosition = getScrollPosition(direction);
		
		while (scrollPosition < maximum) {
			WebElement target = contentElement.findElement(locator);
			if (target != null) {
				driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				return driver.getWidgetForElement(target);
			}
			
			int to = (int) (scrollPosition + singleStep);
			scrollTo(direction, to);
			scrollPosition = getScrollPosition(direction);
		}
		
		//TODO: Find out the original timeout and re-apply it 
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		return null;
	}
	*/
}
