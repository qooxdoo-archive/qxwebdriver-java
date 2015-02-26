package org.qooxdoo.demo.mobileshowcase;


import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.HasTouchScreen;

public class Events extends Mobileshowcase {

	protected static String getEvents = "return [].map.call(qxWeb('.pointers .event'), function(el) { return el.innerText })"; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		scrollTo(0, 5000);
		Thread.sleep(500);
		selectItem("Events");
		verifyTitle("Events");
	}
	
	@Before
	public void init() throws InterruptedException {
		Thread.sleep(250);
	}
	
	@Test
	public void swipe() throws InterruptedException {
		if (!(driver.getWebDriver() instanceof HasTouchScreen)) {
			return;
		}
		
		WidgetImpl area = (WidgetImpl) driver.findWidget(By.xpath("//div[contains(@class, 'container-touch-area')]"));
		area.track(500, 0, 25);
		
		java.util.List<String> eventNames = (List<String>) driver.executeScript(getEvents);
		if (eventNames.size() != 4) {
			logEvents("swipe", eventNames);
		}
		Assert.assertEquals(4, eventNames.size());
		Assert.assertEquals("pointerdown", eventNames.get(0));
		Assert.assertEquals("pointermove", eventNames.get(1));
		Assert.assertEquals("pointerup", eventNames.get(2));
		Assert.assertEquals("swipe", eventNames.get(3));
	}
	
	@Test
	public void tap() {
		WidgetImpl area = (WidgetImpl) driver.findWidget(By.xpath("//div[contains(@class, 'container-touch-area')]"));
		area.tap();
		
		java.util.List<String> eventNames = (List<String>) driver.executeScript(getEvents);
		if (eventNames.size() != 3) {
			logEvents("tap", eventNames);
		}
		Assert.assertEquals(3, eventNames.size());
		Assert.assertEquals("pointerdown", eventNames.get(0));
		Assert.assertEquals("pointerup", eventNames.get(1));
		Assert.assertEquals("tap", eventNames.get(2));
	}
	
	@Test
	public void longtap() throws InterruptedException {
		WidgetImpl area = (WidgetImpl) driver.findWidget(By.xpath("//div[contains(@class, 'container-touch-area')]"));
		area.longtap();
		
		java.util.List<String> eventNames = (List<String>) driver.executeScript(getEvents);
		
		if (eventNames.size() != 4) {
			logEvents("longtap", eventNames);
		}
		Assert.assertEquals(4, eventNames.size());
		Assert.assertEquals("pointerdown", eventNames.get(0));
		Assert.assertEquals("longtap", eventNames.get(1));
		Assert.assertEquals("pointermove", eventNames.get(2));
		Assert.assertEquals("pointerup", eventNames.get(3));
	}
	
	
	protected void logEvents(String testedEvent, List<String> eventNames) {
		System.err.println(testedEvent + " events:");
		Iterator<String> itr = eventNames.iterator();
		while (itr.hasNext()) {
			System.err.println(itr.next());
		}
	}

}
