package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

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
		WebElement userName = driver.findElement(By.xpath("//input[@type='text']"));
		userName.sendKeys("Affe");
		Assert.assertEquals("Affe", userName.getAttribute("value"));
	}
	
	@Test
	public void checkBox() {
		scrollTo(0, 0);
		WebElement checkBox = driver.findElement(By.xpath("//span[contains(@class, 'checkbox')]"));
		String classBefore = checkBox.getAttribute("class");
		Assert.assertFalse(classBefore.contains("checked"));
		tap(checkBox);
		String classAfter = checkBox.getAttribute("class");
		Assert.assertTrue(classAfter.contains("checked"));
	}
	
	@Test
	public void radioButton() {
		scrollTo(0, 0);
		WebElement radioButton = driver.findElement(By.xpath("//span[contains(@class, 'radio')]"));
		String rbclassBefore = radioButton.getAttribute("class");
		Assert.assertFalse(rbclassBefore.contains("checked"));
		tap(radioButton);
		String rbclassAfter = radioButton.getAttribute("class");
		Assert.assertTrue(rbclassAfter.contains("checked"));
	}
	
	@Test
	public void selectBox() {
		scrollTo(0, 1500);
		WebElement selectBox = driver.findElement(By.xpath("//input[contains(@class, 'selectbox')]"));
		Assert.assertEquals("", (String) selectBox.getAttribute("value"));
		tap(selectBox);
		WebElement twitter = driver.findElement(By.xpath("//div[text() = 'Twitter']/ancestor::li[contains(@class, 'list-item')]"));
		tap(twitter);
		try {
			Assert.assertFalse(twitter.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
		Assert.assertEquals("Twitter", (String) selectBox.getAttribute("value"));
	}
	
	@Test
	public void slider() {
		scrollTo(0, 1500);
		WebElement sliderKnob = driver.findElement(By.xpath("//div[contains(@class, 'slider')]/div"));
		
		Dimension size = sliderKnob.getSize();
		int halfWidth = size.getWidth() / 2;
		int halfHeight = size.getHeight() / 2;
		
		Point loc = sliderKnob.getLocation();
		int startX = loc.getX();

		int posX = startX + halfWidth;
		int posY = loc.getY() + halfHeight;
		TouchActions foo = new TouchActions(driver.getWebDriver());
		foo.down(posX, posY);
		while (posX < startX + 110) {
			posX += 10;
			foo.move(posX, posY);
		}
		foo.up(posX, posY)
		.perform();
	}
}
