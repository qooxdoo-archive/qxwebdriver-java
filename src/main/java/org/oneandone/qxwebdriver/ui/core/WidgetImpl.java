/* ************************************************************************

   qxwebdriver-java

   http://github.com/qooxdoo/qxwebdriver-java

   Copyright:
     2012-2013 1&1 Internet AG, Germany, http://www.1und1.de

   License:
     LGPL: http://www.gnu.org/licenses/lgpl.html
     EPL: http://www.eclipse.org/org/documents/epl-v10.php
     See the license.txt file in the project's top-level directory for details.

   Authors:
     * Daniel Wagner (danielwagner)

************************************************************************ */

package org.oneandone.qxwebdriver.ui.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.interactions.Actions;
import org.oneandone.qxwebdriver.resources.JavaScriptRunner;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
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

	public void dragToWidget(Widget target) {
		Actions actions = new Actions(driver.getWebDriver());
		actions.dragAndDrop(getContentElement(), target.getContentElement());
		actions.perform();
	}
	
	public void dragOver(Widget target) throws InterruptedException {
		Mouse mouse = ((HasInputDevices)driver.getWebDriver()).getMouse();
		Locatable root = (Locatable) driver.findElement(By.tagName("body"));
		//cast WebElement to Locatable
		Locatable sourceL = (Locatable)contentElement;
		Locatable targetL = (Locatable)target.getContentElement();
		
		Coordinates coord = root.getCoordinates();
		mouse.mouseDown(sourceL.getCoordinates());
		
		//get source position (center,center)
		int sourceX = sourceL.getCoordinates().onPage().x + ((int) contentElement.getSize().width /2);
		int sourceY = sourceL.getCoordinates().onPage().y + ((int) contentElement.getSize().height /2);
		
		// get target position (center, center)
		int targetX = targetL.getCoordinates().onPage().x + ((int) target.getContentElement().getSize().width /2);
		int targetY = targetL.getCoordinates().onPage().y + ((int) target.getContentElement().getSize().height /2);
		

		//compute deltas between source and target position
		//delta must be positive, however
		//also we have to define the direction 
		int deltaX;
		int directionX=1; //move direction is right
		
		int deltaY;
		int directionY=1; //move direction is bottom
		
		deltaX = targetX-sourceX; 
		if (deltaX < 0){
			deltaX *= -1;
			directionX=-1; // move direction is left
		}
		
		deltaY = targetY-sourceY;
		if(deltaY <0){
			deltaY *= -1;
			directionY=-1; // move direction is top
		}
		
		
		//define base delta, which must be the higher one
		
		int baseDelta = deltaX;
		if (deltaY > deltaX) {
			baseDelta = deltaY;
		}
		
		
		// iterate base delta, set mouse cursor in relation to delta x & delta y
		int x = 0;
		int y = 0;

		for (int i = 1; i <= baseDelta; i+=4) {
			if(i> baseDelta){
				i = baseDelta;
			}
			x = (int) sourceX + (deltaX * i / baseDelta * directionX);
			y = (int) sourceY + (deltaY * i / baseDelta * directionY);
			
			mouse.mouseMove(coord, x, y);
			//System.out.println(x +", "+ y);
			Thread.sleep(1);
			
			}
		// source has the same coordinates as target
		if(sourceX == targetX && sourceY == targetY){
			mouse.mouseMove(targetL.getCoordinates(),x++,y);
			Thread.sleep(20);
		}
	}
	public void drop(Widget target) throws InterruptedException {
		Mouse mouse = ((HasInputDevices)driver.getWebDriver()).getMouse();
		dragOver(target);
		
		Locatable targetL=(Locatable)target.getContentElement();
		mouse.mouseUp(targetL.getCoordinates());
		
	}
	

	public void click() {
		Actions actions = new Actions(driver.getWebDriver());
		actions.moveToElement(getContentElement());
		actions.click();
		actions.perform();
		
	}

	public void sendKeys(CharSequence keysToSend) {
		contentElement.sendKeys(keysToSend);
	}

	public Widget waitForChildControl(String childControlId, Integer timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout, 250);
		return wait.until(childControlIsVisible(childControlId));
	}

	/**
	 * A condition that waits until a child control has been rendered, then
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
	
	public Boolean hasChildControl(String childControlId) {
		Object result = jsRunner.runScript("hasChildControl",
				contentElement, childControlId);
		return (Boolean) result;
	}
	
	public org.oneandone.qxwebdriver.ui.Widget getLayoutParent() {
		Object result = jsRunner.runScript("getLayoutParent",
				contentElement);
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
