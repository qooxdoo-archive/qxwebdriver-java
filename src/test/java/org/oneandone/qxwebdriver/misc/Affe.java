package org.oneandone.qxwebdriver.misc;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Affe {

	static QxWebDriver driver;
	static final String AUT_URL = "http://demo.qooxdoo.org/current/widgetbrowser/index.html";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FirefoxDriver webDriver = new FirefoxDriver();
		//ChromeDriver webDriver = new ChromeDriver();
		driver = new QxWebDriver(webDriver);
		driver.get(AUT_URL);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
	}
}
