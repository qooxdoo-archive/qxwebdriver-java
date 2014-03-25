package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
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
		WebElement toggleButton = driver.findElement(By.xpath("//div[contains(@class, 'togglebutton') and @data-label-checked='ON']"));
		String classBefore = toggleButton.getAttribute("class");
		Thread.sleep(250);
		tap(toggleButton);
		String classAfter = toggleButton.getAttribute("class");
		Assert.assertNotEquals(classBefore, classAfter);
		
		scrollTo(0, 500);
		
		// collapsible
		WebElement collapsibleHeader = driver.findElement(By.xpath("//div[contains(@class, 'collapsible-header')]/ancestor::div[contains(@class, 'group')]"));
		WebElement collapsibleContent = driver.findElement(By.xpath("//div[contains(@class, 'collapsible-content')]"));
		Assert.assertFalse(collapsibleContent.isDisplayed());
		tap(collapsibleHeader);
		Assert.assertTrue(collapsibleContent.isDisplayed());
	}
}
