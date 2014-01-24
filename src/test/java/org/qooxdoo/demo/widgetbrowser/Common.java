package org.qooxdoo.demo.widgetbrowser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
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
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
