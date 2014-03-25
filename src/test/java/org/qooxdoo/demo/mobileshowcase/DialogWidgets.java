package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class DialogWidgets extends Mobileshowcase {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		String title = "Dialog Widgets";
		selectItem(title);
		verifyTitle(title);
	}

//	@Test
//	public void dialogWidgets() throws InterruptedException {
//		
//		popup();
//		menu();
//		busyIndicator();
//		anchorPopup();
//	}
	
	@Test
	public void popup() throws InterruptedException {
		String popupButtonLocator = "//div[text() = 'Popup']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		WebElement popupButton = driver.findElement(By.xpath(popupButtonLocator));
		tap(popupButton);
		
		String closeButtonLocator = "//div[text() = 'Close Popup']/ancestor::div[contains(@class, 'button')]";
		WebElement closeButton = driver.findElement(By.xpath(closeButtonLocator));
		Assert.assertTrue(closeButton.isDisplayed());
		
		Thread.sleep(250);
		tap(closeButton);
		Assert.assertFalse(closeButton.isDisplayed());
	}
	
	@Test
	public void menu() throws InterruptedException {
		String menuButtonLocator = "//div[text() = 'Menu']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		WebElement menuButton = driver.findElement(By.xpath(menuButtonLocator));
		tap(menuButton);
		
		String menuItemLocator = "//div[text() = 'Action 5']/ancestor::li[contains(@class, 'list-item')]";
		WebElement menuItem = driver.findElement(By.xpath(menuItemLocator));
		Assert.assertTrue(menuItem.isDisplayed());
		
		Thread.sleep(250);
		tap(menuItem);
		Thread.sleep(250);
		
		try {
			Assert.assertFalse(menuItem.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
	}
	
	@Test
	public void busyIndicator() throws InterruptedException {
		String busyButtonLocator = "//div[text() = 'Busy Indicator']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		WebElement busyButton = driver.findElement(By.xpath(busyButtonLocator));
		tap(busyButton);
		
		String busyPopupLocator = "//div[contains(text(), 'Please wait')]/ancestor::div[contains(@class, 'popup')]";
		WebElement busyPopup = driver.findElement(By.xpath(busyPopupLocator));
		Assert.assertTrue(busyPopup.isDisplayed());
		
		Thread.sleep(4000);
		Assert.assertFalse(busyPopup.isDisplayed());
	}
	
	@Test
	public void anchorPopup() throws InterruptedException {
		String anchorPopupButtonLocator = "//div[text() = 'Anchor Popup']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		WebElement anchorPopupButton = driver.findElement(By.xpath(anchorPopupButtonLocator));
		tap(anchorPopupButton);
		
		String yesButtonLocator = "//div[text() = 'Yes']/ancestor::div[contains(@class, 'button')]";
		WebElement yesButton = driver.findElement(By.xpath(yesButtonLocator));
		Assert.assertTrue(yesButton.isDisplayed());
		
		Thread.sleep(250);
		tap(yesButton);
		Assert.assertFalse(yesButton.isDisplayed());
	}
}
