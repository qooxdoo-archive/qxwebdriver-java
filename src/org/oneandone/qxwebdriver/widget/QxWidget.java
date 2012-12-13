package org.oneandone.qxwebdriver.widget;

import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QxWidget implements Widget {

	public QxWidget(WebElement element, WebDriver webDriver) {
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
	}
	
	public String qxHash;
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
		String getter = String.format(JavaScript.INSTANCE.getValue("getChildControl"),
				qxHash, childControlId);
		Object result = jsExecutor.executeScript(getter);
		WebElement element = (WebElement) result;
		return element;
	}
	
	public String getPropertyValueAsJson(String propertyName) {
		String getter = String.format(JavaScript.INSTANCE.getValue("getPropertyValueAsJson"),
				qxHash, propertyName);
		Object result = jsExecutor.executeScript(getter);
		return (String) result;
	}
	
	public WebElement getElementFromProperty(String propertyName) {
		String getter = String.format(JavaScript.INSTANCE.getValue("getElementFromProperty"),
				qxHash, propertyName);
		Object result = jsExecutor.executeScript(getter);
		return (WebElement) result;
	}
	
	public List<WebElement> getChildren() {
		String getter = String.format(JavaScript.INSTANCE.getValue("getChildrenElements"),
				qxHash);
		Object result = jsExecutor.executeScript(getter);
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
	
	public WebElement findElement(org.oneandone.qxwebdriver.By by) {
		return contentElement.findElement(by);
	}

}
