package org.qooxdoo.demo.websitewidgetbrowser;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Accordion extends WebsiteWidgetBrowser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WebsiteWidgetBrowser.setUpBeforeClass();
		selectTab("Accordion");
	}
	
	protected String getActivePageText(WebElement tabs) {
		List<WebElement> pages = tabs.findElements(By.xpath("descendant::li[contains(@class, 'qx-tabs-page')]"));
		Iterator<WebElement> itr = pages.iterator();
		while (itr.hasNext()) {
			WebElement page = itr.next();
			if (page.isDisplayed()) {
				return page.findElement(By.xpath("h3")).getText();
			}
		}
		return null;
	}
	
	@Test
	public void accordion() throws InterruptedException {
		WebElement tabs = webDriver.findElement(By.id("accordion-default"));
		List<WebElement> tabButtons = tabs.findElements(By.xpath("descendant::li[contains(@class, 'qx-tabs-button')]/button"));
		Collections.reverse(tabButtons);
		Iterator<WebElement> itr = tabButtons.iterator();
		while (itr.hasNext()) {
			WebElement tabButton = itr.next();
			String buttonText = tabButton.getText();
			tabButton.click();
			Thread.sleep(1000);
			String activePageText = getActivePageText(tabs);
			Assert.assertTrue(activePageText.startsWith(buttonText));
		}
	}
}
