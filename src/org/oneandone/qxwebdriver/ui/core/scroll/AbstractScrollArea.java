package org.oneandone.qxwebdriver.ui.core.scroll;

import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.core.Widget;
import org.oneandone.qxwebdriver.ui.IWidget;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.core.scroll.AbstractScrollArea">ScrollArea</a>
 * widget
 */
public class AbstractScrollArea extends Widget implements Scrollable {

	public AbstractScrollArea(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected IWidget getScrollbar(String direction) {
		String childControlId = "scrollbar-" + direction;
		org.oneandone.qxwebdriver.ui.IWidget scrollBar = waitForChildControl(childControlId, 2);
		return scrollBar;
	}
	
	public void scrollTo(String direction, Integer position) {
		IWidget scrollBar = getScrollbar(direction);
		jsExecutor.executeScript(JavaScript.INSTANCE.getValue("scrollTo"),
				scrollBar.getContentElement(), position);
	}
	
	public Long getScrollPosition(String direction) {
		IWidget scrollBar = getScrollbar(direction);
		return getScrollPosition(scrollBar);
	}
	
	protected Long getScrollPosition(IWidget scrollBar) {
		try {
			String result = scrollBar.getPropertyValueAsJson("position");
			return Long.parseLong(result);
		} catch(com.opera.core.systems.scope.exceptions.ScopeException e) {
			return null;
		}
	}
	
	protected Long getScrollStep(IWidget scrollBar) {
		String result = scrollBar.getPropertyValueAsJson("singleStep");
		return Long.parseLong(result);
	}
	
	public Long getScrollStep(String direction) {
		IWidget scrollBar = getScrollbar(direction);
		return getScrollStep(scrollBar);
	}
	
	public Long getMaximum(String direction) {
		IWidget scrollBar = getScrollbar(direction);
		return getMaximum(scrollBar);
	}
	
	protected Long getMaximum(IWidget scrollBar) {
		String result = scrollBar.getPropertyValueAsJson("maximum");
		return Long.parseLong(result);
	}
	
	public IWidget scrollToChild(String direction, By locator) {
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
