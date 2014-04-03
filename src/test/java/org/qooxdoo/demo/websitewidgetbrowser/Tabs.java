package org.qooxdoo.demo.websitewidgetbrowser;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Tabs extends WebsiteWidgetBrowser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WebsiteWidgetBrowser.setUpBeforeClass();
		selectTab("Tabs");
	}
	
	protected String getActivePageText(WebElement tabs) {
		List<WebElement> pages = tabs.findElements(By.xpath("descendant::div[contains(@class, 'qx-tabs-page')]"));
		Iterator<WebElement> itr = pages.iterator();
		while (itr.hasNext()) {
			WebElement page = itr.next();
			if (page.isDisplayed()) {
				return page.getText();
			}
		}
		return null;
	}
	
	@Test
	public void tabs() throws InterruptedException {
		List<WebElement> alignmentRadios = webDriver.findElements(By.xpath("//div[@id = 'tabs-page']/descendant::input[@name = 'tabalign']"));
		Iterator<WebElement> itr = alignmentRadios.iterator();
		while (itr.hasNext()) {
			WebElement radio = itr.next();
			radio.click();
			testTabs();
		}
	}
	
	protected void testTabs() {
		WebElement tabs = webDriver.findElement(By.xpath("//div[@id = 'tabs-page']/descendant::div[contains(@class, 'qx-tabs')]"));
		List<WebElement> tabButtons = tabs.findElements(By.xpath("descendant::li[contains(@class, 'qx-tabs-button')]/button"));
		Iterator<WebElement> itr = tabButtons.iterator();
		while (itr.hasNext()) {
			WebElement tabButton = itr.next();
			String buttonText = tabButton.getText();
			tabButton.click();
			String activePageText = getActivePageText(tabs);
			Assert.assertEquals(buttonText, activePageText);
		}
	}
}
