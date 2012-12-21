package org.oneandone.qxwebdriver.examples.widgetbrowser;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Common base class for Widget Browser tests
 *
 */
public class WidgetBrowser {
	
	public static String url = "http://demo.qooxdoo.org/current/widgetbrowser/";
	
	public WidgetBrowser() {
		driver = new QxWebDriver(new FirefoxDriver());
		//driver = new QxWebDriver(new ChromeDriver());
		driver.get(url);
	}
	
	protected QxWebDriver driver;
	protected Widget tabPage;
	
	/**
	 * Clicks a button in the Widget Browser's main tab bar
	 */
	public void selectTab(String title) {
		String locator = "qx.ui.container.Composite/qx.ui.container.Scroll/qx.ui.tabview.TabView";
		Selectable tabView = (Selectable) driver.findWidget(By.qxh(locator));
		tabView.selectItem(title);
		
		String tabPageLocator = "qx.ui.tabview.Page";
		tabPage = tabView.findWidget(By.qxh(tabPageLocator));
	}
	
	public void test() {
		System.out.println("Nothing to do.");
	}

}
