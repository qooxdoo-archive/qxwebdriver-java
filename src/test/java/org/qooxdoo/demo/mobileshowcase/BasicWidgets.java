package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.ui.Touchable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BasicWidgets extends Mobileshowcase {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		String title = "Basic Widgets";
		selectItem(title);
		verifyTitle(title);
	}

	@Test
	public void basicWidgets() throws InterruptedException {
		// toggle button
		Touchable toggleButton = (Touchable) driver.findWidget(By.xpath("//div[contains(@class, 'togglebutton') and @data-label-checked='ON']"));
		Boolean valueBefore = (Boolean) toggleButton.getPropertyValue("value");
		Assert.assertFalse(valueBefore);
		Thread.sleep(250);
		toggleButton.tap();
		Boolean valueAfter = (Boolean) toggleButton.getPropertyValue("value");
		Assert.assertTrue(valueAfter);
		
		scrollTo(0, 500);
		Thread.sleep(500);
		
		// collapsible
		Touchable collapsibleHeader = (Touchable) driver.findWidget(By.xpath("//div[contains(@class, 'collapsible-header')]"));
		WebElement collapsibleContent = driver.findElement(By.xpath("//div[contains(@class, 'collapsible-content')]"));
		Assert.assertFalse(collapsibleContent.isDisplayed());
		collapsibleHeader.tap();
		Assert.assertTrue(collapsibleContent.isDisplayed());
	}
}
