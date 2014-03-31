package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.openqa.selenium.WebElement;

public class TabBar extends Mobileshowcase {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		selectItem("Tab Bar");
		verifyTitle("Tabs");
	}

	@Test
	public void drawer() throws InterruptedException {
		String[] tabs = {"Desktop", "Server", "Mobile", "Website"};
		for (String tab : tabs) {
			WebElement tabButton = driver.findElement(By.xpath("//div[text() = '" + tab + "']/ancestor::div[contains(@class, 'tabButton')]"));
			tap(tabButton);
			Thread.sleep(500);
			WebElement tabContent = driver.findElement(By.xpath("//b[text() = 'qx." + tab + "']/ancestor::div[contains(@class, 'content')]"));
			Assert.assertTrue(tabContent.isDisplayed());
		}
	}
}
