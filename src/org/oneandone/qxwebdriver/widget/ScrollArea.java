package org.oneandone.qxwebdriver.widget;

import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.core.scroll.AbstractScrollArea">ScrollArea</a>
 * widget
 */
public class ScrollArea extends Widget implements Scrollable {

	public ScrollArea(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected Widget getScrollbar(String direction) {
		String childControlId = "scrollbar-" + direction;
		Widget scrollBar = waitForChildControl(childControlId, 2);
		return scrollBar;
	}
	
	public void scrollTo(String direction, Integer position) {
		Widget scrollBar = getScrollbar(direction);
		jsExecutor.executeScript(JavaScript.INSTANCE.getValue("scrollTo"),
				scrollBar.contentElement, position);
	}
	
	public Long getScrollPosition(String direction) {
		Widget scrollBar = getScrollbar(direction);
		return getScrollPosition(scrollBar);
	}
	
	protected Long getScrollPosition(Widget scrollBar) {
		try {
			String result = scrollBar.getPropertyValueAsJson("position");
			return Long.parseLong(result);
		} catch(com.opera.core.systems.scope.exceptions.ScopeException e) {
			return null;
		}
	}
	
	protected Long getScrollStep(Widget scrollBar) {
		String result = scrollBar.getPropertyValueAsJson("singleStep");
		return Long.parseLong(result);
	}
	
	public Long getScrollStep(String direction) {
		Widget scrollBar = getScrollbar(direction);
		return getScrollStep(scrollBar);
	}
	
	public Long getMaximum(String direction) {
		Widget scrollBar = getScrollbar(direction);
		return getMaximum(scrollBar);
	}
	
	protected Long getMaximum(Widget scrollBar) {
		String result = scrollBar.getPropertyValueAsJson("maximum");
		return Long.parseLong(result);
	}
	
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

}
