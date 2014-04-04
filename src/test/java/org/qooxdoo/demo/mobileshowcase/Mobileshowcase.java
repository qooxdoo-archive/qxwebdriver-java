package org.qooxdoo.demo.mobileshowcase;

import io.selendroid.SelendroidLauncher;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Touchable;
import org.oneandone.qxwebdriver.ui.mobile.Selectable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		
		driver.registerLogAppender();
		driver.registerGlobalErrorHandler();
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
		String overviewButtonLoc = "//div[text() = 'Overview']/ancestor::div[contains(@class, 'navigationbar-button')]";
		try {
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			Touchable overviewButton = (Touchable) driver.findWidget(By.xpath(overviewButtonLoc));
			System.out.println("Tapping Overview button");
			overviewButton.tap();
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			// wait until the navigation list animation has finished
			Thread.sleep(1000);
		} catch(Exception e) {}
		
		System.out.println("Selecting item '" + title + "'");
		Selectable list = (Selectable) driver.findWidget(By.xpath("//div[contains(@class, 'master-detail-master')]/descendant::ul[contains(@class, 'list')]"));
		list.selectItem(title);
		// wait until the page change animation has finished
		Thread.sleep(1000);
	}
	
	public static void verifyTitle(String title) {
		String titleXpath = "//h1[contains(@class, 'title') and text() = '" + title + "']";
		WebElement titleElement = driver.findElement(By.xpath(titleXpath));
		Assert.assertEquals(title, titleElement.getText());
	}
	
	public static void goBack() throws InterruptedException {
		Touchable backButton = (Touchable) driver.findWidget(By.xpath("//div[contains(@class, 'navigationbar-backbutton')]"));
		if (backButton.isDisplayed()) {
			System.out.println("Going back");
			backButton.tap();
			// wait until the page change animation has finished
			Thread.sleep(1000);
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws InterruptedException {
		goBack();
		IntegrationTest.printQxLog(driver);
		IntegrationTest.printQxErrors(driver);
		
		driver.quit();
		//selendroidServer.stopSelendroid();
	}

}
