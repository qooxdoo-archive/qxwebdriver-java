package org.qooxdoo.demo.mobileshowcase;

import io.selendroid.SelendroidLauncher;

import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.qooxdoo.demo.Configuration;
import org.qooxdoo.demo.IntegrationTest;

public abstract class Mobileshowcase extends IntegrationTest {
	
	public static SelendroidLauncher selendroidServer;
	public static QxWebDriver driver;
	public static WebDriver webDriver;
	
	protected String navigationList = "//div[contains(@class, 'layout-card')]/descendant::div[contains(@class, 'group')]";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//SelendroidConfiguration config = new SelendroidConfiguration();
		//selendroidServer = new SelendroidLauncher(config);
		//selendroidServer.lauchSelendroid();

		driver = Configuration.getQxWebDriver();
		webDriver = driver.getWebDriver();
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
	}
	
	public static void tap(WebElement item) {
		String browser = System.getProperty("org.qooxdoo.demo.browsername");
		if (browser.equals("android")) {
			TouchActions tap = new TouchActions(driver.getWebDriver()).singleTap(item);
			tap.perform();
		} else {
			item.click();
		}
	}
	
	public static void scrollTo(int x, int y) {
		String script = "qx.ui.mobile.core.Widget.getWidgetById(arguments[0].id).scrollTo(" + x + ", " + y + ")";
		List<WebElement> scrollers = driver.findElements(By.className("scroll"));
		Iterator<WebElement> itr = scrollers.iterator();
		while (itr.hasNext()) {
			WebElement scroller = itr.next();
			if (scroller.isDisplayed()) {
				driver.executeScript(script, scroller);
			}
		}
	}
	
	public static void selectItem(String title) throws InterruptedException {
		System.out.println("Selecting item '" + title + "'");
		String xpath = "//div[contains(@class, 'list-itemlabel') and text() = '" + title + "']/ancestor::li";
		WebElement item = driver.findElement(By.xpath(xpath));
		tap(item);
		// wait until the page change animation has finished
		Thread.sleep(1000);
	}
	
	public static void verifyTitle(String title) {
		String titleXpath = "//h1[contains(@class, 'title') and text() = '" + title + "']";
		WebElement titleElement = driver.findElement(By.xpath(titleXpath));
		Assert.assertEquals(title, titleElement.getText());
	}
	
	public static void goBack() throws InterruptedException {
		WebElement backButton = driver.findElement(By.xpath("//div[contains(@class, 'navigationbar-backbutton')]"));
		if (backButton.isDisplayed()) {
			System.out.println("Going back");
			tap(backButton);
			// wait until the page change animation has finished
			Thread.sleep(1000);
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws InterruptedException {
		goBack();
		driver.quit();
		//selendroidServer.stopSelendroid();
	}

}
