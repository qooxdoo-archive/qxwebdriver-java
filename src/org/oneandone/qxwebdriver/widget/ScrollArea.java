package org.oneandone.qxwebdriver.widget;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollArea extends QxWidget {

	public ScrollArea(WebElement element, WebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected WebElement getScrollbar(String direction) {
		String childControlId = "scrollbar-" + direction;
		waitForChildControl(childControlId, 2);
		WebElement scrollBar = getChildControl(childControlId);
		return scrollBar;
	}
	
	protected static String SCROLL_TO = "qx.ui.core.Widget.getWidgetByElement(arguments[0]).scrollTo(arguments[1]);";
	
	public void scrollTo(String direction, Integer position) {
		WebElement scrollBar = getScrollbar(direction);
		jsExecutor.executeScript(SCROLL_TO, scrollBar, position);
	}
	
	protected static String GET_SCROLL_STEP = "return qx.ui.core.Widget.getWidgetByElement(arguments[0]).getSingleStep()";
	
	protected static String GET_MAXIMUM = "return qx.ui.core.Widget.getWidgetByElement(arguments[0]).getMaximum()";
	
	protected static String GET_POSITION = "return qx.ui.core.Widget.getWidgetByElement(arguments[0]).getPosition()";
	
	private Long getScrollPosition(WebElement scrollBar) {
		try {
		  return (Long) jsExecutor.executeScript(GET_POSITION, scrollBar);
		} catch(com.opera.core.systems.scope.exceptions.ScopeException e) {
			return null;
		}
	}
	
	public WebElement scrollToChild(String direction, org.openqa.selenium.By locator) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		
		WebElement scrollBar = getScrollbar(direction);
		Long singleStep = (Long) jsExecutor.executeScript(GET_SCROLL_STEP, scrollBar);
		Long maximum = (Long) jsExecutor.executeScript(GET_MAXIMUM, scrollBar);
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
