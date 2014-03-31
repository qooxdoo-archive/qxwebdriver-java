package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Touchable;
import org.openqa.selenium.WebElement;

public class TabBar extends Mobileshowcase {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		selectItem("Tab Bar");
		verifyTitle("Tabs");
	}

	@Test
	public void tabBar() throws InterruptedException {
		String[] tabs = {"Desktop", "Server", "Mobile", "Website"};
		for (String tab : tabs) {
			Touchable tabButton = (Touchable) driver.findWidget(By.xpath("//div[text() = '" + tab + "']/ancestor::div[contains(@class, 'tabButton')]"));
			tabButton.tap();
			Thread.sleep(500);
			WebElement tabContent = driver.findElement(By.xpath("//b[text() = 'qx." + tab + "']/ancestor::div[contains(@class, 'content')]"));
			Assert.assertTrue(tabContent.isDisplayed());
		}
	}
}
