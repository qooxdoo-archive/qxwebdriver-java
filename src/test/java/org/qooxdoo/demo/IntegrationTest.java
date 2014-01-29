package org.qooxdoo.demo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.QxWebDriver;

public abstract class IntegrationTest {
	
	public static QxWebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Configuration.getQxWebDriver();
		driver.manage().window().maximize();
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//driver.close();
		driver.quit();
	}
}
