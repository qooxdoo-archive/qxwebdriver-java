package org.oneandone.qxwebdriver.widget;

import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.QxWebDriver;
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
	
	protected Long getScrollPosition(Widget scrollBar) {
		//Widget scrollBarWidget = new Widget(scrollBar, driver);
		try {
			String result = scrollBar.getPropertyValueAsJson("position");
			return Long.parseLong(result);
		} catch(com.opera.core.systems.scope.exceptions.ScopeException e) {
			return null;
		}
	}
	
	protected Long getScrollStep(Widget scrollBar) {
		//Widget scrollBarWidget = new Widget(scrollBar, driver);
		String result = scrollBar.getPropertyValueAsJson("singleStep");
		return Long.parseLong(result);
	}
	
	protected Long getMaximum(Widget scrollBar) {
		//Widget scrollBarWidget = new Widget(scrollBar, driver);
		String result = scrollBar.getPropertyValueAsJson("maximum");
		return Long.parseLong(result);
	}
	
	public Widget scrollToChild(String direction, org.oneandone.qxwebdriver.By locator) {
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		
		Widget scrollBar = getScrollbar(direction);
		Long singleStep = getScrollStep(scrollBar);
		Long maximum = getMaximum(scrollBar);
		Long scrollPosition = getScrollPosition(scrollBar);
		
		while (scrollPosition < maximum) {
			int to = (int) (scrollPosition + singleStep);
			scrollTo(direction, to);
			
			WebElement target = contentElement.findElement(locator);
			if (target != null) {
				driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				return driver.getWidgetForElement(target);
			}

			scrollPosition = getScrollPosition(scrollBar);
		}
		
		//TODO: Find out the original timeout and re-apply it 
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		return null;
	}

}
