package org.oneandone.qxwebdriver.widget;

import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.Widget;
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
		contentElement = (WebElement) jsExecutor.executeScript(GET_CONTENT_ELEMENT, element);
		qxHash = (String) jsExecutor.executeScript(GET_QX_HASH, element);
	}
	
	public String qxHash;
	public WebElement contentElement;
	public WebDriver driver;
	public JavascriptExecutor jsExecutor;
	
	static public final String GET_CONTENT_ELEMENT = "var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);" +
			"return widget.getContentElement().getDomElement();";
	
	static protected String GET_CHILD_CONTROL = "var widget = qx.core.ObjectRegistry.fromHashCode('%1$s');" +
			"return widget.getChildControl('%2$s').getContentElement().getDomElement();";
	
	static public final String GET_QX_HASH = "return qx.ui.core.Widget.getWidgetByElement(arguments[0]).toHashCode()";
	
	//static protected String GET_PROPERTY = "return qx.core.ObjectRegistry.fromHashCode('%1$s').get('%2$s')";
	
	static protected String GET_PROPERTY_JSON = "var json = window.JSON || qx.lang.Json;" +
			"var obj = qx.core.ObjectRegistry.fromHashCode('%1$s');" +
			"var val = obj.get('%2$s');" +
			"return json.stringify(val);";
	
	static protected String GET_CHILD_ELEMENTS = "var childElements = [];" + 
			"var widget = qx.core.ObjectRegistry.fromHashCode('%1$s');" +
			"widget.getChildren().forEach(function(child) {" +
			"  if (child.getContentElement() && child.getContentElement().getDomElement()) {" +
			"    childElements.push(child.getContentElement().getDomElement())" +
			"  }" +
			"});" +
			"return childElements;";
	
	static protected String GET_ELEMENTS_FROM_PROPERTY_AFFE = "var elements = [];" +
			"var widget = qx.core.ObjectRegistry.fromHashCode('%1$s');" +
			"var propVal = widget.get('%2$s');" +
			"if (!propVal instanceof Array) {" +
			"  propVal = [propVal];" +
			"}" +
			"propVal.forEach(function(item) {" +
			"  if (item && item.getContentElement && item.getContentElement() && item.getContentElement().getDomElement()) {" +
			"    elements.push(item);" +
			"  }" +
			"});" +
			"return elements;";
	
	static protected String GET_ELEMENTS_FROM_PROPERTY = "var widget = qx.core.ObjectRegistry.fromHashCode('%1$s');" +
			"var propVal = widget.get('%2$s');" +
			"return propVal.getContentElement().getDomElement();";
	
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
		String getter = String.format(GET_CHILD_CONTROL, qxHash, childControlId);
		Object result = jsExecutor.executeScript(getter);
		WebElement element = (WebElement) result;
		return element;
	}
	
	public String getSerializedPropertyValue(String propertyName) {
		String getter = String.format(GET_PROPERTY_JSON, qxHash, "children");
		Object result = jsExecutor.executeScript(getter);
		return (String) result;
	}
	
	public WebElement getElementFromProperty(String propertyName) {
		String getter = String.format(GET_ELEMENTS_FROM_PROPERTY, qxHash, propertyName);
		Object result = jsExecutor.executeScript(getter);
		return (WebElement) result;
	}
	
	public List<WebElement> getChildren() {
		String getter = String.format(GET_CHILD_ELEMENTS, qxHash);
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
