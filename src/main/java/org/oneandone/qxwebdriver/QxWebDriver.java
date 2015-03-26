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

package org.oneandone.qxwebdriver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.log.LogEntry;
import org.oneandone.qxwebdriver.resources.JavaScript;
import org.oneandone.qxwebdriver.resources.JavaScriptRunner;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.WidgetFactory;
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
public class QxWebDriver implements WebDriver, JavascriptExecutor {

	public QxWebDriver(WebDriver webdriver) {
		driver = webdriver;
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		widgetFactory = new org.oneandone.qxwebdriver.ui.DefaultWidgetFactory(this);
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
				Object result = null;
				String script = JavaScript.INSTANCE.getValue("isApplicationReady");
				try {
					result = jsExecutor.executeScript(script);
				} catch(org.openqa.selenium.WebDriverException e) {
					
				}
				Boolean isReady = (Boolean) result;
				return isReady;
			}

			@Override
			public String toString() {
				return "qooxdoo application is ready.";
			}
		};
	}
	
	public JavascriptExecutor jsExecutor;
	public JavaScriptRunner jsRunner;
	private WebDriver driver;
	private WidgetFactory widgetFactory;
	
	
	/**
	 * Returns the original WebDriver instance
	 */
	public WebDriver getWebDriver() {
		return driver;
	}
	
	/**
	 * Find the first matching {@link Widget} using the given method.
	 * 
	 * @param by The locating mechanism
	 * @param timeoutInSeconds time to wait for the widget
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching widget was found before the timeout elapsed
     * @see org.oneandone.qxwebdriver.By
	 */
	protected Widget findWidget(By by, long timeoutInSeconds) throws NoSuchElementException {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		WebElement element;
		try {
		  element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch(org.openqa.selenium.TimeoutException e) {
			throw new NoSuchElementException("Unable to find element for locator.", e);
		}
		return getWidgetForElement(element);
	}
	
	/**
	 * Find the first matching {@link Widget} using the given method. Retry for up to 5 seconds before
	 * throwing.
	 * 
	 * @param by The locating mechanism
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching widget was found before the timeout elapsed
     * @see org.oneandone.qxwebdriver.By
	 */
	public Widget findWidget(By by) {
		return findWidget(by, 5);
	}
	
	/**
	 * Find the first matching {@link Widget} using the given method.
	 * 
	 * @param by The locating mechanism
	 * @param timeoutInSeconds time to wait for the widget
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching widget was found before the timeout elapsed
     * @see org.oneandone.qxwebdriver.By
	 */
	public Widget waitForWidget(By by, long timeoutInSeconds) {
		return findWidget(by, timeoutInSeconds);
	}
	
	/**
	 * Returns an instance of {@link Widget} or one of its subclasses that
	 * represents the qooxdoo widget containing the given element.
	 * @param element A WebElement representing a DOM element that is part of a
	 * qooxdoo widget
	 * @return Widget object
	 */
	public Widget getWidgetForElement(WebElement element) {
		return widgetFactory.getWidgetForElement(element);
	}
	
	/**
	 * Registers a new log appender with the AUT's logging system. Entries can be
	 * accessed using getLogEvents()
	 */
	public void registerLogAppender() {
		jsRunner.runScript("registerLogAppender");
	}
	
	/**
	 * Retrieves the AUT's qx log entries. registerLogAppender() *must* be called
	 * before this can be used.
	 */
	public List<LogEntry> getLogEvents() {
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		List<String> jsonEntries =  (List<String>) jsRunner.runScript("getAllLogEvents");
		Iterator<String> itr = jsonEntries.iterator();
		while (itr.hasNext()) {
			String json = itr.next();
			LogEntry entry = new LogEntry(json);
			logEntries.add(entry);
		}
		return logEntries;
	}
	
	/**
	 * Registers a global error handler using qx.event.GlobalError.setErrorHandler
	 * Caught exceptions can be retrieved using getCaughtErrors
	 */
	public void registerGlobalErrorHandler() {
		jsRunner.runScript("registerGlobalErrorHandler");
	}
	
	/**
	 * Retrieves any exceptions caught by qooxdoo's global error handling. 
	 * registerGlobalErrorHandler *must* be called before this can be used.
	 */
	public List<String> getCaughtErrors() {
		return (List<String>) jsRunner.runScript("getCaughtErrors");
	}
	
	/**
	 * Uses qooxdoo's localization support to get the currently active locale's translation for a string
	 */
	public String getTranslation(String string) {
		String js = "return qx.locale.Manager.getInstance().translate('" + string + "', []).toString();";
		return (String) jsExecutor.executeScript(js, string);
	}
	
	/**
	 * Uses qooxdoo's localization support to get a specific locale's translation for a string
	 */
	public String getTranslation(String string, String locale) {
		String js = "return qx.locale.Manager.getInstance().translate('" + string + "', [], '" + locale + "').toString();";
		return (String) jsExecutor.executeScript(js, string);
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
		waitForQxApplication();
		init();
	}
	
	/**
	 * Wait until qx.core.Init.getApplication() returns something truthy.
	 */
	public void waitForQxApplication() {
		new WebDriverWait(driver, 30, 250).until(qxAppIsReady());
	}
	
	/**
	 * Initializes the testing environment.
	 */
	public void init() {
		jsRunner = new JavaScriptRunner(jsExecutor);
		// make sure getWidgetByElement is defined so other scripts can use it
		jsRunner.defineFunction("getWidgetByElement");
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

	@Override
	public Object executeAsyncScript(String arg0, Object... arg1) {
		return jsExecutor.executeAsyncScript(arg0, arg1);
	}

	@Override
	public Object executeScript(String arg0, Object... arg1) {
		return jsExecutor.executeScript(arg0, arg1);
	}

}
