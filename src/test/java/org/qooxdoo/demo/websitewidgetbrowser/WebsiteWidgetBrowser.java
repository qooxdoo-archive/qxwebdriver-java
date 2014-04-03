package org.qooxdoo.demo.websitewidgetbrowser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.qooxdoo.demo.Configuration;
import org.qooxdoo.demo.IntegrationTest;

public abstract class WebsiteWidgetBrowser extends IntegrationTest {
	
	public static WebDriver webDriver;

	public static void selectTab(String title) {
		String xpath = "//div[contains(@class, 'qx-tabs')]/descendant::button[text() = '" + title + "']";
		WebElement button = webDriver.findElement(By.xpath(xpath));
		button.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		webDriver = Configuration.getWebDriver();
		webDriver.manage().window().maximize();
		webDriver.get(System.getProperty("org.qooxdoo.demo.auturl"));
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		webDriver.quit();
	}
}
