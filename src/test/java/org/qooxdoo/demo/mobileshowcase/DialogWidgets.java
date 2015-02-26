package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.ui.Touchable;
import org.oneandone.qxwebdriver.ui.mobile.Selectable;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
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
	
	@Test
	public void popup() throws InterruptedException {
		String popupButtonLocator = "//div[text() = 'Popup']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		Touchable popupButton = (Touchable) driver.findWidget(By.xpath(popupButtonLocator));
		popupButton.tap();
		
		String closeButtonLocator = "//div[text() = 'Close Popup']/ancestor::div[contains(@class, 'button')]";
		Touchable closeButton = (Touchable) driver.findWidget(By.xpath(closeButtonLocator));
		Assert.assertTrue(closeButton.isDisplayed());
		
		Thread.sleep(250);
		closeButton.tap();
		Assert.assertFalse(closeButton.isDisplayed());
	}
	
	@Test
	public void menu() throws InterruptedException {
		String menuButtonLocator = "//div[text() = 'Menu']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		Touchable menuButton = (Touchable) driver.findWidget(By.xpath(menuButtonLocator));
		menuButton.tap();
		
		By listLocator = By.xpath("//div[contains(@class, 'menu')]/descendant::ul[contains(@class, 'list')]");
		Selectable list = (Selectable) driver.findWidget(listLocator);
		Assert.assertTrue(list.isDisplayed());

		list.selectItem(5);
		Thread.sleep(500);
		Assert.assertFalse(list.isDisplayed());
	}
	
	@Test
	public void busyIndicator() throws InterruptedException {
		String busyButtonLocator = "//div[text() = 'Busy Indicator']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		Touchable busyButton = (Touchable) driver.findWidget(By.xpath(busyButtonLocator));
		busyButton.tap();
		
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
		Touchable anchorPopupButton = (Touchable) driver.findWidget(By.xpath(anchorPopupButtonLocator));
		anchorPopupButton.tap();
		
		String yesButtonLocator = "//div[text() = 'Yes']/ancestor::div[contains(@class, 'button')]";
		Touchable yesButton = (Touchable) driver.findWidget(By.xpath(yesButtonLocator));
		Assert.assertTrue(yesButton.isDisplayed());
		
		Thread.sleep(250);
		yesButton.tap();
		Assert.assertFalse(yesButton.isDisplayed());
	}
	
	@Test
	public void anchorMenu() throws InterruptedException {
		String anchorMenuButtonLocator = "//div[text() = 'Anchor Menu']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		Touchable anchorMenuButton = (Touchable) driver.findWidget(By.xpath(anchorMenuButtonLocator));
		anchorMenuButton.tap();
		
		String greenButtonLocator = "//div[text() = 'Green']/ancestor::li[contains(@class, 'list-item')]";
		WebElement greenButton = driver.findElement(By.xpath(greenButtonLocator));
		Assert.assertTrue(greenButton.isDisplayed());
		
		Thread.sleep(250);
		WidgetImpl.tap(driver.getWebDriver(), greenButton);

		try {
			Assert.assertFalse(greenButton.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
	}
	
	@Test
	public void picker() throws InterruptedException {
		String pickerButtonLocator = "//div[text() = 'Picker']/ancestor::div[contains(@class, 'button')]";
		Thread.sleep(250);
		Touchable pickerButton = (Touchable) driver.findWidget(By.xpath(pickerButtonLocator));
		pickerButton.tap();
		
		String chooseButtonLocator = "//div[text() = 'OK']/ancestor::div[contains(@class, 'button')]";
		Touchable chooseButton = (Touchable) driver.findWidget(By.xpath(chooseButtonLocator));
		Assert.assertTrue(chooseButton.isDisplayed());
		
		Thread.sleep(250);
		chooseButton.tap();
		Assert.assertFalse(chooseButton.isDisplayed());
	}
}
