package org.oneandone.qxwebdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.oneandone.qxwebdriver.ui.IWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A Decorator that wraps a {@link org.openqa.selenium.WebDriver} object, 
 * adding qooxdoo-specific features.
 * Note that the WebDriver used <strong>must</strong> implement the 
 * {@link org.openqa.selenium.JavascriptExecutor} interface.
 */
public class QxWebDriver implements WebDriver {

	public QxWebDriver(WebDriver webdriver) {
		driver = webdriver;
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		widgetFactory = new org.oneandone.qxwebdriver.ui.WidgetFactory(this);
	}
	
	public QxWebDriver(WebDriver webdriver, WidgetFactory widgetFactory) {
		driver = webdriver;
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
	/**
	 * A condition that waits until the qooxdoo application in the browser is
	 * ready (<code>qx.core.Init.getApplication()</code> returns anything truthy).
	 */
	public ExpectedCondition<Boolean> qxAppIsReady() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("isApplicationReady"));
				Boolean isReady = (Boolean) result;
				return isReady;
			}

			@Override
			public String toString() {
				return "qooxdoo application is ready.";
			}
		};
	}
	
	public WebDriver driver;
	private JavascriptExecutor jsExecutor;
	private WidgetFactory widgetFactory;
	
	/**
	 * Returns a list of qooxdoo interfaces implemented by the widget containing
	 * the given element.
	 */
	public List<String> getWidgetInterfaces(WebElement element) {
		String script = JavaScript.INSTANCE.getValue("getInterfaces");
		return (List<String>) jsExecutor.executeScript(script, element);
	}
	
	/**
	 * Returns the inheritance hierarchy of the widget containing the given 
	 * element.
	 */
	public List<String> getWidgetInheritance(WebElement element) {
		String script = JavaScript.INSTANCE.getValue("getInheritance");
		return (List<String>) jsExecutor.executeScript(script, element);
	}
	
	/**
	 * Find the first matching {@link IWidget.Widget} using the given method.
	 * 
	 * @param by The locating mechanism
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching elements are found
     * @see org.oneandone.qxwebdriver.By
	 */
	public IWidget findWidget(By by) throws NoSuchElementException {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement element;
		try {
		  element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch(org.openqa.selenium.TimeoutException e) {
			throw new NoSuchElementException("Unable to find element for locator.", e);
		}
		return getWidgetForElement(element);
	}
	
	/**
	 * Returns an instance of {@link IWidget.Widget} or one of its subclasses that
	 * represents the qooxdoo widget containing the given element.
	 * @param element A WebElement representing a DOM element that is part of a
	 * qooxdoo widget
	 * @return Widget object
	 */
	public IWidget getWidgetForElement(WebElement element) {
		List<String> interfaces = getWidgetInterfaces(element);
		List<String> classes = getWidgetInheritance(element);
		
		classes.addAll(interfaces);
		
		return widgetFactory.getWidgetForElement(element, classes);
	}

	@Override
	public void close() {
		driver.close();
	}

	@Override
	public WebElement findElement(By arg0) {
		return driver.findElement(arg0);
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		return driver.findElements(arg0);
	}

	@Override
	public void get(String arg0) {
		driver.get(arg0);
		new WebDriverWait(driver, 10, 250).until(qxAppIsReady());
	}

	@Override
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	@Override
	public String getPageSource() {
		return driver.getPageSource();
	}

	@Override
	public String getTitle() {
		return driver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	@Override
	public Options manage() {
		return driver.manage();
	}

	@Override
	public Navigation navigate() {
		return driver.navigate();
	}

	@Override
	public void quit() {
		driver.quit();
	}

	@Override
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

}
