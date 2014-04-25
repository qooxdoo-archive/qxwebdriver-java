package org.qooxdoo.demo.desktopapiviewer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ClassViewer extends DesktopApiViewer {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DesktopApiViewer.setUpBeforeClass();
		String className = "qx.ui.core.Widget";
		selectClass(className);
	}

	@Test
	public void links() {
		String internalTarget = "#capture";
		WebElement internalLink= driver.findElement(By.xpath("//a[text()='" + internalTarget + "']"));
		internalLink.click();
		String hashAfter = (String) driver.executeScript("return location.hash;");
		Assert.assertEquals("#qx.ui.core.Widget~capture", hashAfter);
		
		String subClass = "qx.ui.basic.Atom";
		WebElement subClassLink = driver.findElement(By.xpath("//a[text()='" + subClass + "']"));
		subClassLink.click();
		
		Widget tabButton = driver.findWidget(By.qxh(tabButtonPath));
		Assert.assertEquals(subClass, tabButton.getPropertyValue("label"));
		hashAfter = (String) driver.executeScript("return location.hash;");
		Assert.assertEquals("#qx.ui.basic.Atom", hashAfter);
	}
	
	@Test
	public void toggleDetail() {
		String detailHeadlinePath = "//div[contains(@class, 'info-panel')]/descendant::div[contains(@class, 'item-detail-headline')]";
		try {
			driver.findElement(By.xpath(detailHeadlinePath));
			Assert.assertTrue("Constructor details should be hidden initially!", false);
		} catch(NoSuchElementException e) {}
		
		WebElement constructorDetailToggle = driver.findElement(By.xpath("//div[contains(@class, 'info-panel')]/descendant::td[contains(@class, 'toggle')]/img"));
		constructorDetailToggle.click();
		WebElement detailHeadline = driver.findElement(By.xpath(detailHeadlinePath));
		Assert.assertTrue(detailHeadline.isDisplayed());
		
		constructorDetailToggle.click();
		try {
			detailHeadline = driver.findElement(By.xpath(detailHeadlinePath));
			Assert.assertTrue("Constructor details could not be hidden!", false);
		} catch(NoSuchElementException e) {}
	}
	
}
