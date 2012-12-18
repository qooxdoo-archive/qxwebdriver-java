package org.oneandone.qxwebdriver;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.oneandone.qxwebdriver.widget.BooleanFormItem;
import org.oneandone.qxwebdriver.widget.ComboBox;
import org.oneandone.qxwebdriver.widget.MenuButton;
import org.oneandone.qxwebdriver.widget.TabView;
import org.oneandone.qxwebdriver.widget.VirtualComboBox;
import org.oneandone.qxwebdriver.widget.VirtualList;
import org.oneandone.qxwebdriver.widget.VirtualSelectBox;
import org.oneandone.qxwebdriver.widget.Widget;
import org.oneandone.qxwebdriver.widget.ScrollArea;
import org.oneandone.qxwebdriver.widget.SelectBox;
import org.oneandone.qxwebdriver.widget.Menu;
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
	 * Find the first matching {@link widget.Widget} using the given method.
	 * 
	 * @param by The locating mechanism
     * @return The first matching element on the current page
     * @throws NoSuchElementException If no matching elements are found
     * @see org.oneandone.qxwebdriver.By
	 */
	public Widget findWidget(By by) throws NoSuchElementException {
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
	 * Returns an instance of {@link widget.Widget} or one of its subclasses that
	 * represents the qooxdoo widget containing the given element.
	 * @param element A WebElement representing a DOM element that is part of a
	 * qooxdoo widget
	 * @return Widget object
	 */
	public Widget getWidgetForElement(WebElement element) {
		List<String> interfaces = getWidgetInterfaces(element);
		List<String> classes = getWidgetInheritance(element);
		
		Iterator<String> classIter = classes.iterator();
		
		while(classIter.hasNext()) {
			String className = classIter.next();
			if (className.equals("qx.ui.form.SelectBox")) {
				return new SelectBox(element, this);
			}
			
			if (className.equals("qx.ui.form.VirtualSelectBox")) {
				return new VirtualSelectBox(element, this);
			}
			
			if (className.equals("qx.ui.form.ComboBox")) {
				return new ComboBox(element, this);
			}
			
			if (className.equals("qx.ui.form.VirtualComboBox")) {
				return new VirtualComboBox(element, this);
			}
			
			if (className.equals("qx.ui.menu.Menu")) {
				return new Menu(element, this);
			}
			
			if (className.equals("qx.ui.form.List")) {
				return new org.oneandone.qxwebdriver.widget.List(element, this);
			}
			
			if (className.equals("qx.ui.list.List")) {
				return new VirtualList(element, this);
			}
			
			if (className.equals("qx.ui.tabview.TabView")) {
				return new TabView(element, this);
			}
			
			if (className.equals("qx.ui.core.scroll.AbstractScrollArea")) {
				return new ScrollArea(element, this);
			}
			
			if (className.equals("qx.ui.form.MenuButton")) {
				return new MenuButton(element, this);
			}
		}
		
		Iterator<String> interfaceIter = interfaces.iterator();
		
		while(interfaceIter.hasNext()) {
			String interfaceName = interfaceIter.next();
			if (interfaceName.equals("qx.ui.form.IBooleanForm")) {
				return new BooleanFormItem(element, this);
			}
		}
		
		return new Widget(element, this);
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
