package org.qooxdoo.demo.websitewidgetbrowser;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Rating extends WebsiteWidgetBrowser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WebsiteWidgetBrowser.setUpBeforeClass();
		selectTab("Rating");
	}
	
	@Test
	public void ratingDefault() {
		WebElement rating = webDriver.findElement(By.id("rating-default"));
		rating(rating);
	}
	
	@Test
	public void ratingLength() {
		WebElement rating = webDriver.findElement(By.id("rating-length"));
		rating(rating);
	}
	
	@Test
	public void ratingNote() {
		WebElement rating = webDriver.findElement(By.id("rating-note"));
		rating(rating);
	}
	
	@Test
	public void ratingHeart() {
		WebElement rating = webDriver.findElement(By.xpath("//div[contains(@class, 'qx-rating-heart')]"));
		rating(rating);
	}
	
	public void rating(WebElement rating) {
		List<WebElement> items = rating.findElements(By.xpath("descendant::*[contains(@class, 'qx-rating-item')]"));
		WebElement lastItem = items.get(items.size() - 1);
		lastItem.click();
		
		String getValue = "return qxWeb(arguments[0]).getValue();";
		JavascriptExecutor exec = (JavascriptExecutor) webDriver;
		Long valueMax = (Long) exec.executeScript(getValue, rating);
		
		Assert.assertEquals(new Long(items.size()), valueMax);
		
		WebElement firstItem = items.get(0);
		firstItem.click();
		
		Long valueMin = (Long) exec.executeScript(getValue, rating);
		Assert.assertEquals(new Long(1), valueMin);
	}
}
