package org.qooxdoo.demo.websitewidgetbrowser;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Slider extends WebsiteWidgetBrowser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WebsiteWidgetBrowser.setUpBeforeClass();
		selectTab("Slider");
	}
	
	protected void drag(WebElement element, int x, int y) {
		Actions mouseAction = new Actions(webDriver);
		mouseAction.dragAndDropBy(element, x, y);
		mouseAction.perform();
	}
	
	@Test
	public void slider() throws InterruptedException {
		String getValue = "return qxWeb(arguments[0]).getValue();";
		JavascriptExecutor exec = (JavascriptExecutor) webDriver;
		
		List<WebElement> sliders = webDriver.findElements(By.xpath("//div[@id = 'slider-page']/descendant::div[contains(@class, 'qx-slider')]"));
		Assert.assertEquals(2, sliders.size());
		Iterator<WebElement> itr = sliders.iterator();
		while (itr.hasNext()) {
			WebElement slider = itr.next();
			Long valueBefore = (Long) exec.executeScript(getValue, slider);
			WebElement knob = slider.findElement(By.xpath("button[contains(@class, 'qx-slider-knob')]"));
			drag(knob, 150, 0);
			Long valueAfter = (Long) exec.executeScript(getValue, slider);
			Assert.assertTrue(valueAfter > valueBefore);
		}
	}
}
