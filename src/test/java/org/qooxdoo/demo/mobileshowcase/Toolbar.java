package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class Toolbar extends Mobileshowcase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		selectItem("Toolbar");
		verifyTitle("Toolbar");
	}
	
	@Test
	public void search() {
		WebElement button = driver.findElement(By.xpath("//div[text() = 'Search']/ancestor::div[contains(@class, 'button')]"));
		tap(button);
		WebElement popupButton = driver.findElement(By.xpath("//div[text() = 'Search']/ancestor::div[contains(@class, 'popup-content')]/descendant::div[contains(@class, 'button')]"));
		tap(popupButton);
		Assert.assertFalse(popupButton.isDisplayed());
	}
	
	@Test
	public void back() {
		WebElement button = driver.findElement(By.xpath("//img[contains(@src, 'arrowleft')]/ancestor::div[contains(@class, 'button')]"));
		tap(button);
		WebElement popupButton = driver.findElement(By.xpath("//div[text() = 'Are you sure?']/ancestor::div[contains(@class, 'popup-content')]/descendant::div[contains(@class, 'button')]"));
		tap(popupButton);
		Assert.assertFalse(popupButton.isDisplayed());
	}
	
	@Test
	public void camera() throws InterruptedException {
		WebElement button = driver.findElement(By.xpath("//img[contains(@src, 'camera')]/ancestor::div[contains(@class, 'button')]"));
		tap(button);
		
		WebElement popup = driver.findElement(By.xpath("//div[text() = 'Data connection...']/ancestor::div[contains(@class, 'popup-content')]"));
		Assert.assertTrue(popup.isDisplayed());
		Thread.sleep(5000);
		Assert.assertFalse(popup.isDisplayed());
	}
	
	@Test
	public void delete() {
		WebElement button = driver.findElement(By.xpath("//div[text() = 'Delete']/ancestor::div[contains(@class, 'button')]"));
		tap(button);
		WebElement popupButton = driver.findElement(By.xpath("//div[text() = 'Are you sure?']/ancestor::div[contains(@class, 'popup-content')]/descendant::div[contains(@class, 'button')]"));
		tap(popupButton);
		try {
			Assert.assertFalse(popupButton.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
	}
}
