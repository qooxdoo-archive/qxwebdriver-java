package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasTouchScreen;

public class Events extends Mobileshowcase {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		scrollTo(0, 5000);
		Thread.sleep(500);
		selectItem("Events");
		verifyTitle("Events");
	}
	
	@Test
	public void swipe() throws InterruptedException {
		if (!(driver.getWebDriver() instanceof HasTouchScreen)) {
			return;
		}
		
		WidgetImpl area = (WidgetImpl) driver.findWidget(By.xpath("//div[contains(@class, 'container-touch-area')]"));
		area.track(500, 0, 25);
		
		java.util.List<WebElement> events = driver.findElements(By.xpath("//span[@class = 'event']"));
		Assert.assertEquals(4, events.size());
		
		Assert.assertEquals("pointerdown", events.get(0).getText());
		Assert.assertEquals("pointermove", events.get(1).getText());
		Assert.assertEquals("pointerup", events.get(2).getText());
		Assert.assertEquals("swipe", events.get(3).getText());
	}
	
	@Test
	public void tap() {
		WidgetImpl area = (WidgetImpl) driver.findWidget(By.xpath("//div[contains(@class, 'container-touch-area')]"));
		area.tap();
		
		java.util.List<WebElement> events = driver.findElements(By.xpath("//span[@class = 'event']"));
		Assert.assertEquals(3, events.size());
		
		Assert.assertEquals("pointerdown", events.get(0).getText());
		Assert.assertEquals("pointerup", events.get(1).getText());
		Assert.assertEquals("tap", events.get(2).getText());
	}
	
	@Test
	public void longtap() throws InterruptedException {
		WidgetImpl area = (WidgetImpl) driver.findWidget(By.xpath("//div[contains(@class, 'container-touch-area')]"));
		area.longtap();
		
		java.util.List<WebElement> events = driver.findElements(By.xpath("//span[@class = 'event']"));
		if (driver.getWebDriver() instanceof HasTouchScreen) {
			Assert.assertEquals(3, events.size());
			
			Assert.assertEquals("pointerdown", events.get(0).getText());
			Assert.assertEquals("longtap", events.get(1).getText());
			Assert.assertEquals("pointerup", events.get(2).getText());
		}
		else {
			// interactions.Mouse always generates a mousemove before mouseup
			Assert.assertEquals(4, events.size());
			
			Assert.assertEquals("pointerdown", events.get(0).getText());
			Assert.assertEquals("longtap", events.get(1).getText());
			Assert.assertEquals("pointermove", events.get(2).getText());
			Assert.assertEquals("pointerup", events.get(3).getText());
		}
	}

}
