package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class FormElements extends Mobileshowcase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		selectItem("Form Elements");
		verifyTitle("Form");
	}
	
	@Test
	public void textInput() {
		scrollTo(0, 0);
		Widget input = driver.findWidget(By.xpath("//input[@type='text']"));
		input.sendKeys("Affe");
		Assert.assertEquals("Affe", input.getPropertyValue("value"));
	}
	
	@Test
	public void checkBox() {
		scrollTo(0, 0);
		Widget checkBox = driver.findWidget(By.xpath("//span[contains(@class, 'checkbox')]"));
		// value is an empty string until the checkbox has been selected/deselected
		Assert.assertEquals("", checkBox.getPropertyValue("value"));
		tap(checkBox.getContentElement());
		Assert.assertTrue((Boolean) checkBox.getPropertyValue("value"));
	}
	
	@Test
	public void radioButton() {
		scrollTo(0, 0);
		Widget radioButton = driver.findWidget(By.xpath("//span[contains(@class, 'radio')]"));
		// value is an empty string until the radio button has been selected/deselected
		Assert.assertEquals("", radioButton.getPropertyValue("value"));
		tap(radioButton.getContentElement());
		Assert.assertTrue((Boolean) radioButton.getPropertyValue("value"));
	}
	
	@Test
	public void selectBox() {
		scrollTo(0, 1500);
		Widget selectBox = driver.findWidget(By.xpath("//input[contains(@class, 'selectbox')]"));
		Assert.assertEquals("", (String) selectBox.getPropertyValue("value"));
		tap(selectBox.getContentElement());
		WebElement twitter = driver.findElement(By.xpath("//div[text() = 'Twitter']/ancestor::li[contains(@class, 'list-item')]"));
		tap(twitter);
		try {
			Assert.assertFalse(twitter.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
		Assert.assertEquals("Twitter", selectBox.getPropertyValue("value"));
	}
	
	

	@Test
	public void slider() throws InterruptedException {
		scrollTo(0, 1500);
		WidgetImpl slider = (WidgetImpl) driver.findWidget(By.xpath("//div[contains(@class, 'slider')]"));
		Long valueBefore = (Long) slider.getPropertyValue("value");

		slider.track(200, 0, 10);
		Long valueAfter = (Long) slider.getPropertyValue("value");
		Assert.assertTrue(valueBefore < valueAfter);
	}
}
