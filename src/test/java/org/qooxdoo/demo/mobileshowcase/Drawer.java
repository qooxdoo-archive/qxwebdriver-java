package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.openqa.selenium.WebElement;

public class Drawer extends Mobileshowcase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		selectItem("Drawer");
		verifyTitle("Drawer");
	}
	
	@Test
	public void drawer() throws InterruptedException {
		String[] drawers = {"top", "right", "bottom", "left"};
		for (String drawer : drawers) {
			WebElement drawerButton = driver.findElement(By.xpath("//div[text() = 'Open " + drawer + " drawer']/ancestor::div[contains(@class, 'button')]"));
			tap(drawerButton);
			Thread.sleep(500);
			WebElement closeButton = driver.findElement(By.xpath("//label[text() = 'This is the " + drawer + " drawer.']/parent::div/div[contains(@class, 'button')]"));
			tap(closeButton);
			Thread.sleep(500);
			Assert.assertFalse(closeButton.isDisplayed());
		}
	}
}
