package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Touchable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasTouchScreen;

public class DataBinding extends Mobileshowcase {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		scrollTo(0, 5000);
		Thread.sleep(500);
		selectItem("Data Binding");
		verifyTitle("Data Binding");
	}
	
	@Test
	public void slider() throws InterruptedException {
		if (!(driver.getWebDriver() instanceof HasTouchScreen)) {
			return;
		}
		Touchable input = (Touchable) driver.findWidget(By.xpath("//input"));
		int valueBefore = Integer.parseInt((String) input.getPropertyValue("value"));
		
		Touchable slider = (Touchable) driver.findWidget(By.xpath("//div[contains(@class, 'slider')]"));
		slider.track(200, 0, 10);
		
		int valueAfter = Integer.parseInt((String) input.getPropertyValue("value"));
		Assert.assertTrue(valueAfter > valueBefore);
	}
	
	@Test
	public void time() throws InterruptedException {
		Touchable button = (Touchable) driver.findWidget(By.xpath("//div[text() = 'Take Time Snapshot']/ancestor::div[contains(@class, 'button')]"));
		button.tap();
		WebElement entry = driver.findElement(By.xpath("//div[text() = 'Stop #1']"));
		Assert.assertTrue(entry.isDisplayed());
	}

}
