package org.qooxdoo.demo.websitewidgetbrowser;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DatePicker extends WebsiteWidgetBrowser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WebsiteWidgetBrowser.setUpBeforeClass();
		selectTab("Date Picker");
	}
	
	@Test
	public void openClose() {
		String calendarPath = "//div[contains(@class, 'qx-calendar')]";
		WebElement picker = webDriver.findElement(By.id("datepicker-default"));
		String valueBefore = picker.getAttribute("value");
		
		WebElement calendar = webDriver.findElement(By.xpath(calendarPath));
		Assert.assertFalse(calendar.isDisplayed());
		picker.click();
		
		Assert.assertTrue(calendar.isDisplayed());
		
		WebElement header = webDriver.findElement(By.xpath("//h1"));
		header.click();
		Assert.assertFalse(calendar.isDisplayed());
		
		picker.click();
		WebElement nextMonth = calendar.findElement(By.xpath("descendant::button[@class='qx-calendar-next']"));
		nextMonth.click();
		Assert.assertTrue(calendar.isDisplayed());
		
		WebElement day = calendar.findElement(By.xpath("descendant::button[text()='4']"));
		day.click();
		Assert.assertFalse(calendar.isDisplayed());
		
		String valueAfter = picker.getAttribute("value");
		Assert.assertNotEquals(valueBefore, valueAfter);
	}
}
