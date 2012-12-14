package org.oneandone.qxwebdriver.widget;

import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollArea extends Widget implements Scrollable {

	public ScrollArea(WebElement element, WebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected WebElement getScrollbar(String direction) {
		String childControlId = "scrollbar-" + direction;
		waitForChildControl(childControlId, 2);
		WebElement scrollBar = getChildControl(childControlId);
		return scrollBar;
	}
	
	public void scrollTo(String direction, Integer position) {
		WebElement scrollBar = getScrollbar(direction);
		jsExecutor.executeScript(JavaScript.INSTANCE.getValue("scrollTo"),
				scrollBar, position);
	}
	
	protected Long getScrollPosition(WebElement scrollBar) {
		Widget scrollBarWidget = new Widget(scrollBar, driver);
		try {
			String result = scrollBarWidget.getPropertyValueAsJson("position");
			return Long.parseLong(result);
		} catch(com.opera.core.systems.scope.exceptions.ScopeException e) {
			return null;
		}
	}
	
	protected Long getScrollStep(WebElement scrollBar) {
		Widget scrollBarWidget = new Widget(scrollBar, driver);
		String result = scrollBarWidget.getPropertyValueAsJson("singleStep");
		return Long.parseLong(result);
	}
	
	protected Long getMaximum(WebElement scrollBar) {
		Widget scrollBarWidget = new Widget(scrollBar, driver);
		String result = scrollBarWidget.getPropertyValueAsJson("maximum");
		return Long.parseLong(result);
	}
	
	public WebElement scrollToChild(String direction, org.openqa.selenium.By locator) {
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		
		WebElement scrollBar = getScrollbar(direction);
		Long singleStep = getScrollStep(scrollBar);
		Long maximum = getMaximum(scrollBar);
		Long scrollPosition = getScrollPosition(scrollBar);
		
		while (scrollPosition < maximum) {
			int to = (int) (scrollPosition + singleStep);
			scrollTo(direction, to);
			
			try {
				WebElement target = contentElement.findElement(locator);
				if (target != null) {
					driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
					return target;
				}
			}
			//TODO: catch NoSuchElementException when By.qxh throws it 
			catch(Exception e) {
			}
			scrollPosition = getScrollPosition(scrollBar);
		}
		
		//TODO: Find out the original timeout and re-apply it 
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		return null;
	}

}
