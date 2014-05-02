package org.qooxdoo.demo.websitewidgetbrowser;

import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Calendar extends WebsiteWidgetBrowser {
	
	protected String[] monthNamesDefault = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	protected String[] monthNamesCustom = {"Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"};
	protected static int month;
	protected static int year;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WebsiteWidgetBrowser.setUpBeforeClass();
		selectTab("Calendar");
		
		Date date = new Date();
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		year = cal.get(java.util.Calendar.YEAR);
		month = cal.get(java.util.Calendar.MONTH);
	}
	
	@Test
	public void calendarDefault() throws InterruptedException {
		testCalendar("calendar-default");
	}
	
	@Test
	public void calendarCustom() throws InterruptedException {
		testCalendar("calendar-custom");
	}
	
	public void testCalendar(String id) throws InterruptedException {
		By calHeaderLoc = By.xpath("descendant::td[contains(@class, 'qx-calendar-month')]");
		By prevMonthLoc = By.xpath("descendant::button[contains(@class, 'qx-calendar-prev')]");
		By nextMonthLoc = By.xpath("descendant::button[contains(@class, 'qx-calendar-next')]");
		
		String[] monthNames;
		if (id.contains("custom")) {
			monthNames = monthNamesCustom;
		} else {
			monthNames = monthNamesDefault;
		}
		
		WebElement calendar = webDriver.findElement(By.id(id));
		WebElement calHeader = calendar.findElement(calHeaderLoc);
		Assert.assertEquals(monthNames[month] + " " + year, calHeader.getText());
		
		String getValue = "return qxWeb('#" + id + "').getValue()";
		JavascriptExecutor exec = (JavascriptExecutor) webDriver;
		String valueBefore = (String) exec.executeScript(getValue);
		
		WebElement prevMonth = calendar.findElement(prevMonthLoc);
		prevMonth.click();
		// refresh the elements because the calendar re-renders itself if the displayed month changes
		calendar = webDriver.findElement(By.id(id));
		calHeader = calendar.findElement(calHeaderLoc);
		
		int prevMonthIdx;
		int prevYear = year;
		if (month == 0) {
			prevMonthIdx = 11;
			prevYear = prevYear - 1;
		} else {
			prevMonthIdx = month - 1;
		}
		String prevMonthName = monthNames[prevMonthIdx];
		Assert.assertEquals(prevMonthName + " " + prevYear, calHeader.getText());
		
		WebElement nextMonth = calendar.findElement(nextMonthLoc);
		nextMonth.click();
		nextMonth = calendar.findElement(nextMonthLoc);
		nextMonth.click();
		// refresh the elements because the calendar re-renders itself if the displayed month changes
		calendar = webDriver.findElement(By.id(id));
		calHeader = calendar.findElement(calHeaderLoc);
		
		int nextMonthIdx;
		int nextYear = year;
		if (month == 11) {
			nextMonthIdx = 0;
			nextYear = nextYear + 1;
		} else {
			nextMonthIdx = month + 1;
		}
		String nextMonthName = monthNames[nextMonthIdx];
		Assert.assertEquals(nextMonthName + " " + nextYear, calHeader.getText());
		
		WebElement day = calendar.findElement(By.xpath("descendant::button[contains(@class, 'qx-calendar-day') and text() = '17']"));
		day.click();
		Thread.sleep(250);
		String getDateString = getValue + ".toString()";
		String valueAfter = (String) exec.executeScript(getDateString);
		Assert.assertNotEquals(valueBefore, valueAfter);
		String nextMonthNameEn = monthNamesDefault[nextMonthIdx];
		nextMonthNameEn = nextMonthNameEn.substring(0, 3);
		System.out.println("valueAfter " + valueAfter);
		System.out.println(" " + nextMonthNameEn + " 17 " + nextYear);
		Assert.assertTrue(valueAfter.contains(" " + nextMonthNameEn + " 17 " + nextYear));
	}
}
