package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasTouchScreen;

public class List extends Mobileshowcase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		String title = "List";
		selectItem(title);
		verifyTitle(title);
	}
	
	@Test
	public void selectItem() {
		WebElement item = driver.findElement(By.xpath("//div[text() = 'Item #3']/ancestor::li"));
		tap(item);
		WebElement selected = driver.findElement(By.xpath("//div[text() = 'You selected Item #3']"));
		Assert.assertTrue(selected.isDisplayed());
		WebElement ok = driver.findElement(By.xpath("//div[text() = 'You selected Item #3']/ancestor::div[contains(@class, 'popup-content')]/descendant::div[contains(@class, 'dialog-button')]"));
		tap(ok);
		try {
			Assert.assertFalse(selected.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
	}
	
	@Test
	public void removeItem() throws InterruptedException {
		if (!(driver.getWebDriver() instanceof HasTouchScreen)) {
			return;
		}
		WebElement item = driver.findElement(By.xpath("//div[text() = 'Item #6']"));
		WidgetImpl.track(driver.getWebDriver(), item, 700, 0, 10);
		Thread.sleep(1000);
		try {
			Assert.assertFalse(item.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
	}
}
