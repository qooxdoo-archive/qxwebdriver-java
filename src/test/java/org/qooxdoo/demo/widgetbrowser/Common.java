package org.qooxdoo.demo.widgetbrowser;

import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.log.LogEntry;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Common {

	static QxWebDriver driver;
	static final String AUT_URL = "http://demo.qooxdoo.org/current/widgetbrowser/index.html";

	static Widget tabPage;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FirefoxDriver webDriver = new FirefoxDriver();
		// ChromeDriver webDriver = new ChromeDriver();
		// DesiredCapabilities capabilities =
		// DesiredCapabilities.internetExplorer();
		// RemoteWebDriver webDriver = new RemoteWebDriver(new
		// URL("http://172.17.14.65:4440/wd/hub"), capabilities);
		driver = new QxWebDriver(webDriver);
		driver.manage().window().maximize();
		driver.get(AUT_URL);
		driver.registerLogAppender();
		driver.registerGlobalErrorHandler();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// Print the AUT's log messages
		List<LogEntry> logEntries = driver.getLogEvents();
		Iterator<LogEntry> logItr = logEntries.iterator();
		while (logItr.hasNext()) {
			System.out.println(logItr.next());
		}
		
		// Print AUT exceptions
		List<String> caughtErrors = (List<String>) driver.getCaughtErrors();
		Iterator exItr = caughtErrors.iterator();
		while (exItr.hasNext()) {
			System.err.println(exItr.next());
		}
		
		driver.close();
	}

	/**
	 * Clicks a button in the Widget Browser's main tab bar
	 */
	public static void selectTab(String title) {
		String locator = "qx.ui.container.Composite/qx.ui.container.Scroll/qx.ui.tabview.TabView";
		Selectable tabView = (Selectable) driver.findWidget(By.qxh(locator));
		tabView.selectItem(title);

		String tabPageLocator = "qx.ui.tabview.Page";
		tabPage = tabView.findWidget(By.qxh(tabPageLocator));
	}
}
