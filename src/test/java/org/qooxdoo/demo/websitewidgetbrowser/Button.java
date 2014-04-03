package org.qooxdoo.demo.websitewidgetbrowser;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Button extends WebsiteWidgetBrowser {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WebsiteWidgetBrowser.setUpBeforeClass();
		selectTab("Button");
	}
	
	@Test
	public void menuButton() {
		WebElement menu = webDriver.findElement(By.id("menu"));
		Assert.assertFalse(menu.isDisplayed());
		
		WebElement menuButton = webDriver.findElement(By.id("menu-button"));
		menuButton.click();
		Assert.assertTrue(menu.isDisplayed());
		
		menuButton.click();
		Assert.assertFalse(menu.isDisplayed());
		
		menuButton.click();
		Assert.assertTrue(menu.isDisplayed());
		
		WebElement header = webDriver.findElement(By.xpath("//h1"));
		header.click();
		Assert.assertFalse(menu.isDisplayed());
	}
}
