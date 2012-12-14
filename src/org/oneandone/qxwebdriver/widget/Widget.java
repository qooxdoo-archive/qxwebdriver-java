package org.oneandone.qxwebdriver.widget;

import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Widget {

	public Widget(WebElement element, WebDriver webDriver) {
		driver = webDriver;
		if (webDriver instanceof QxWebDriver) {
			QxWebDriver qwd = (QxWebDriver) webDriver;
			driver = qwd.driver;
		}
		
		jsExecutor = (JavascriptExecutor) driver;
		
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
	public WebDriver driver;
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
	
	public WebElement getChildControl(String childControlId) {
		Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getChildControl"),
				contentElement, childControlId);
		WebElement element = (WebElement) result;
		return element;
	}
	
	public String getPropertyValueAsJson(String propertyName) {
		Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getPropertyValueAsJson"),
				contentElement, propertyName);
		return (String) result;
	}
	
	public WebElement getElementFromProperty(String propertyName) {
		Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getElementFromProperty"),
				contentElement, propertyName);
		return (WebElement) result;
	}
	
	public List<WebElement> getChildren() {
		Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("getChildrenElements"), 
				contentElement);
		List<WebElement> children = (List<WebElement>) result;
		return children;
	}
	
	public WebElement getChild(String text) {
		List<WebElement> children = getChildren();
		Iterator<WebElement> iter = children.iterator();
		while (iter.hasNext()) {
			WebElement child = iter.next();
			if (child.getText().contains(text)) {
				return child;
			}
		}
		return null;
	}
	
	public ExpectedCondition<WebElement> isRendered(final WebElement contentElement, final By by) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return contentElement.findElement(by);
			}

			@Override
			public String toString() {
				return "qooxdoo application is ready.";
			}
		};
	}
	
	public WebElement findElement(org.oneandone.qxwebdriver.By by) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		return wait.until(isRendered(contentElement, by));
	}
	
	public String toString() {
		return "QxWidget " + classname +  "[" + qxHash + "]";
	}

}
