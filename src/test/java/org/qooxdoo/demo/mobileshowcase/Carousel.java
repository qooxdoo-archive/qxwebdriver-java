package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasTouchScreen;

public class Carousel extends Mobileshowcase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		selectItem("Carousel");
		verifyTitle("Carousel");
	}
	
	@Test
	public void carousel() throws InterruptedException {
		if (!(driver.getWebDriver() instanceof HasTouchScreen)) {
			return;
		}
		java.util.List<WebElement> pages = driver.findElements(By.xpath("//div[contains(@class, 'carousel-page')]"));
		
		WebElement label0 = driver.findElement(By.xpath("//div[contains(text(), 'This is a carousel')]"));
		Assert.assertTrue(label0.isDisplayed());
		
		WidgetImpl.track(driver.getWebDriver(), pages.get(0), -350, 0, 50);
		Thread.sleep(1000);
		
		WebElement label1 = driver.findElement(By.xpath("//div[contains(text(), 'It contains multiple carousel pages')]"));
		Assert.assertTrue(label1.isDisplayed());
		
		WidgetImpl.track(driver.getWebDriver(), pages.get(1), -350, 0, 50);
		Thread.sleep(1000);
		
		WebElement label2 = driver.findElement(By.xpath("//div[contains(text(), 'Carousel pages may contain')]"));
		Assert.assertTrue(label2.isDisplayed());
		
		WebElement nextPage = driver.findElement(By.xpath("//div[text() = 'Next Page']/ancestor::div[contains(@class, 'button')]"));
		tap(nextPage);
		Thread.sleep(1000);
		
		WebElement label3 = driver.findElement(By.xpath("//div[contains(text(), 'The carousel snaps')]"));
		Assert.assertTrue(label3.isDisplayed());
		
		WidgetImpl.track(driver.getWebDriver(), pages.get(3), -350, 0, 50);
		Thread.sleep(1000);
		
		WebElement label4 = driver.findElement(By.xpath("//div[contains(text(), 'You can add as many')]"));
		Assert.assertTrue(label4.isDisplayed());
		
		java.util.List<WebElement> paginationLabels = driver.findElements(By.xpath("//div[contains(@class, 'carousel-pagination-label')]"));
		Assert.assertEquals(6, paginationLabels.size());
		
		WebElement addButton = driver.findElement(By.xpath("//div[text() = 'Add more pages']/ancestor::div[contains(@class, 'button')]"));
		tap(addButton);
		
		paginationLabels = driver.findElements(By.xpath("//div[contains(@class, 'carousel-pagination-label')]"));
		Assert.assertEquals(56, paginationLabels.size());
	}
}
