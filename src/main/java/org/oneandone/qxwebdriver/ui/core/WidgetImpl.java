package org.oneandone.qxwebdriver.ui.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.resources.JavaScriptRunner;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WidgetImpl implements org.oneandone.qxwebdriver.ui.Widget {

	public WidgetImpl(WebElement element, QxWebDriver webDriver) {
		driver = webDriver;
		
		jsExecutor = driver.jsExecutor;
		jsRunner = driver.jsRunner;
		
		contentElement = (WebElement) jsRunner.runScript("getContentElement",
				element);
	}
	
	private String qxHash = null;

	private String classname = null;

	protected WebElement contentElement;

	protected QxWebDriver driver;

	protected JavascriptExecutor jsExecutor;

	protected JavaScriptRunner jsRunner;

	public String getQxHash() {
		if (qxHash == null) {
			qxHash = (String) jsRunner.runScript("getObjectHash", 
					contentElement);
		}
		return qxHash;
	}
	
	public String getClassname() {
		if (classname == null) {
			classname = (String) jsRunner.runScript("getClassname", 
					contentElement);
		}
		return classname;
	}
	
	public WebElement getContentElement() {
		return contentElement;
	}
	
	public void click() {
		contentElement.click();
	}
	
	public void sendKeys(CharSequence keysToSend) {
		contentElement.sendKeys(keysToSend);
	}
	
	public org.oneandone.qxwebdriver.ui.Widget waitForChildControl(String childControlId, Integer timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout, 250);
		return wait.until(childControlIsVisible(childControlId));
	}
	
	/**
	 * A conditon that waits until a child control has been rendered, then 
	 * returns it.
	 */
	public ExpectedCondition<org.oneandone.qxwebdriver.ui.Widget> childControlIsVisible(final String childControlId) {
		return new ExpectedCondition<org.oneandone.qxwebdriver.ui.Widget>() {
			@Override
			public org.oneandone.qxwebdriver.ui.Widget apply(WebDriver webDriver) {
				org.oneandone.qxwebdriver.ui.Widget childControl = getChildControl(childControlId);
				if (childControl != null && childControl.isDisplayed()) {
					return childControl;
				}
				return null;
			}

			@Override
			public String toString() {
				return "Child control is visible.";
			}
		};
	}
	
	public org.oneandone.qxwebdriver.ui.Widget getChildControl(String childControlId) {
		Object result = jsRunner.runScript("getChildControl",
				contentElement, childControlId);
		WebElement element = (WebElement) result;
		if (element == null) {
			return null;
		}
		return driver.getWidgetForElement(element);
	}
	
	public Object executeJavascript(String script) {
		return jsExecutor.executeScript(script, contentElement);
	}
	
	public String getPropertyValueAsJson(String propertyName) {
		Object result = jsRunner.runScript("getPropertyValueAsJson",
				contentElement, propertyName);
		return (String) result;
	}
	
	public Object getPropertyValue(String propertyName) {
		Object result = jsRunner.runScript("getPropertyValue",
				contentElement, propertyName);
		return result;
	}
	
	private WebElement getElementFromProperty(String propertyName) {
		Object result = jsRunner.runScript("getElementFromProperty",
				contentElement, propertyName);
		return (WebElement) result;
	}
	
	/**
	 * Returns a {@link WidgetImpl} representing the value of a widget property,
	 * e.g. <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.MenuButton~menu!property">the 
	 * MenuButton's menu property</a>
	 */
	public Widget getWidgetFromProperty(String propertyName) {
		return driver.getWidgetForElement(getElementFromProperty(propertyName));
	}
	
	/**
	 * Returns a {@link WidgetImpl} representing the value of a widget property,
	 * e.g. <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.MenuButton~menu!property">the 
	 * MenuButton's menu property</a>
	 */
	public List<Widget> getWidgetListFromProperty(String propertyName) {
		List<WebElement> elements = (List<WebElement>) jsRunner.runScript("getElementsFromProperty", contentElement, propertyName);
		List<Widget> widgets = new ArrayList<Widget>();
		
		Iterator<WebElement> elemIter = elements.iterator();
		while(elemIter.hasNext()) {
			WebElement element = elemIter.next();
			Widget widget = driver.getWidgetForElement(element);
			widgets.add(widget);
		}
		
		return widgets;
	}
	
	private List<WebElement> getChildrenElements() {
		Object result = jsRunner.runScript("getChildrenElements", 
				contentElement);
		List<WebElement> children = (List<WebElement>) result;
		return children;
	}
	
	public List<org.oneandone.qxwebdriver.ui.Widget> getChildren() {
		List<WebElement> childrenElements = getChildrenElements();
		Iterator<WebElement> iter = childrenElements.iterator();
		List<org.oneandone.qxwebdriver.ui.Widget> children = new ArrayList<org.oneandone.qxwebdriver.ui.Widget>();
		
		while(iter.hasNext()) {
			WebElement child = iter.next();
			children.add(driver.getWidgetForElement(child));
		}
		
		return children;
	}
	
	/**
	 * A condition that checks if an element is rendered.
	 */
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
	
	public WebElement findElement(org.openqa.selenium.By by) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		return wait.until(isRendered(contentElement, by));
	}
	
	/**
	 * Finds a widget relative to the current one by traversing the qooxdoo
	 * widget hierarchy.
	 */
	public org.oneandone.qxwebdriver.ui.Widget findWidget(org.openqa.selenium.By by) {
		WebElement element = findElement(by);
		return driver.getWidgetForElement(element);
	}
	
	public String toString() {
		return "QxWidget " + getClassname() +  "[" + getQxHash() + "]";
	}

	/**
	 * Not implemented for qooxdoo widgets.
	 */
	public void submit() {
		throw new RuntimeException("Not implemented for qooxdoo widgets.");
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
		return contentElement.isSelected();
	}

	@Override
	public boolean isEnabled() {
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

	/**
	 * Determines if the widget is visible by querying the qooxdoo property 
	 * <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.core.Widget~isSeeable!method_public">seeable</a>.
	 */
	public boolean isDisplayed() {
		//return (Boolean) getPropertyValue("seeable");
		return (Boolean) executeJavascript("return qx.ui.core.Widget.getWidgetByElement(arguments[0]).isSeeable()");
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
