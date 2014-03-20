package org.qooxdoo.demo.mobileshowcase;

import io.selendroid.SelendroidConfiguration;
import io.selendroid.SelendroidLauncher;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.qooxdoo.demo.Configuration;
import org.qooxdoo.demo.IntegrationTest;

public class Mobileshowcase extends IntegrationTest {
	
	public static SelendroidLauncher selendroidServer;
	public static QxWebDriver driver;
	
	public static String navigationList = "//div[contains(@class, 'layout-card')]/descendant::div[contains(@class, 'group')]";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SelendroidConfiguration config = new SelendroidConfiguration();

		selendroidServer = new SelendroidLauncher(config);
		selendroidServer.lauchSelendroid();

		driver = Configuration.getQxWebDriver();
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
	}
	
	@Test
	public void helloWorld() throws InterruptedException {
		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());
		driver.quit();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		driver.quit();
		selendroidServer.stopSelendroid();
	}

}
