package org.oneandone.qxwebdriver.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Widget implements WebElement {

	public Widget(WebElement element, QxWebDriver webDriver) {
		driver = webDriver;
		/*
		if (webDriver instanceof QxWebDriver) {
			QxWebDriver qwd = (QxWebDriver) webDriver;
			driver = qwd.driver;
		}
		*/
		
		jsExecutor = (JavascriptExecutor) driver.driver;
		
		contentElement = (WebElement) jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getContentElement"),
				element);
		
		qxHash = (String) jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getObjectHash"), 
				element);
		
		classname = (String) jsExecutor.executeScript("return qx.ui.core.Widget.getWidgetByElement(arguments[0]).classname", 
				contentElement);
	}
	
	public String qxHash;
	public String classname;
	public WebElement contentElement;
	public QxWebDriver driver;
	public JavascriptExecutor jsExecutor;
	
	public void click() {
		contentElement.click();
	}
	
	public void sendKeys(CharSequence keysToSend) {
		contentElement.sendKeys(keysToSend);
	}
	
	public void waitForChildControl(String childControlId, Integer timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout, 250);
		wait.until(childControlIsVisible(childControlId));
	}
	
	public ExpectedCondition<Boolean> childControlIsVisible(final String childControlId) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement childControl = getChildControl(childControlId);
				if (childControl == null) {
					return false;
				}
				return childControl.isDisplayed();
			}

			@Override
			public String toString() {
				return "Child control is visible.";
			}
		};
	}
	
	public Widget getChildControl(String childControlId) {
		Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getChildControl"),
				contentElement, childControlId);
		WebElement element = (WebElement) result;
		return driver.getWidgetForElement(element);
	}
	
	public String getPropertyValueAsJson(String propertyName) {
		Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getPropertyValueAsJson"),
				contentElement, propertyName);
		return (String) result;
	}
	
	private WebElement getElementFromProperty(String propertyName) {
		Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getElementFromProperty"),
				contentElement, propertyName);
		return (WebElement) result;
	}
	
	public Widget getWidgetFromProperty(String propertyName) {
		return driver.getWidgetForElement(getElementFromProperty(propertyName));
	}
	
	private List<WebElement> getChildrenElements() {
		Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getChildrenElements"), 
				contentElement);
		List<WebElement> children = (List<WebElement>) result;
		return children;
	}
	
	public List<Widget> getChildren() {
		List<WebElement> childrenElements = getChildrenElements();
		Iterator<WebElement> iter = childrenElements.iterator();
		List<Widget> children = new ArrayList<Widget>();
		
		while(iter.hasNext()) {
			WebElement child = iter.next();
			children.add(driver.getWidgetForElement(child));
		}
		
		return children;
	}
	
	public ExpectedCondition<WebElement> isRendered(final WebElement contentElement, final By by) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return contentElement.findElement(by);
			}

			@Override
			public String toString() {
				return "element is rendered.";
			}
		};
	}
	
	public WebElement findElement(org.oneandone.qxwebdriver.By by) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		return wait.until(isRendered(contentElement, by));
	}
	
	public Widget findWidget(org.oneandone.qxwebdriver.By by) {
		WebElement element = findElement(by);
		return driver.getWidgetForElement(element);
	}
	
	public String toString() {
		return "QxWidget " + classname +  "[" + qxHash + "]";
	}

	@Override
	public void submit() {
		// TODO Call execute if widget is executable?
		contentElement.submit();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		contentElement.sendKeys(keysToSend);
		
	}

	@Override
	public void clear() {
		contentElement.clear();
	}

	@Override
	public String getTagName() {
		return contentElement.getTagName();
	}

	@Override
	public String getAttribute(String name) {
		return contentElement.getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		// TODO: qx-specific implementation if possible
		return contentElement.isSelected();
	}

	@Override
	public boolean isEnabled() {
		// TODO: qx-specific implementation if possible
		return contentElement.isEnabled();
	}

	@Override
	public String getText() {
		return contentElement.getText();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return contentElement.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return contentElement.findElement(by);
	}

	@Override
	public boolean isDisplayed() {
		//TODO: use qx's isSeeable?
		return contentElement.isDisplayed();
	}

	@Override
	public Point getLocation() {
		return contentElement.getLocation();
	}

	@Override
	public Dimension getSize() {
		return contentElement.getSize();
	}

	@Override
	public String getCssValue(String propertyName) {
		return contentElement.getCssValue(propertyName);
	}

}
