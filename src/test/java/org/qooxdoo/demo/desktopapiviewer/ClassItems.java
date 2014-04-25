package org.qooxdoo.demo.desktopapiviewer;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ClassItems extends DesktopApiViewer {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DesktopApiViewer.setUpBeforeClass();
		String className = "qx.ui.table.pane.Scroller";
		selectClass(className);
	}

	protected void testProperties() {
		String propertiesPath = "*/qx.ui.toolbar.ToolBar/*/[@label=Properties]";
		Widget propertiesButton = driver.findWidget(By.qxh(propertiesPath));
		boolean propertiesActive = (Boolean) propertiesButton.getPropertyValue("value"); 
		Assert.assertTrue(propertiesActive);
		String propertyItemPath = "//div[contains(@class, 'info-panel')]/descendant::span[text()='getLiveResize']";
		WebElement propertyItem = driver.findElement(By.xpath(propertyItemPath));
		Assert.assertTrue(propertyItem.isDisplayed());
		propertiesButton.click();
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			propertyItem = driver.findElement(By.xpath(propertyItemPath));
			Assert.assertTrue("Property method was not hidden!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		propertiesButton.click();
	}
	
	protected void testClassItem(String item, String method) {
		String itemPath = "*/qx.ui.toolbar.ToolBar/*/[@label=" + item + "]";
		Widget itemButton = driver.findWidget(By.qxh(itemPath));
		boolean itemActive = (Boolean) itemButton.getPropertyValue("value"); 
		Assert.assertFalse(itemActive);
		String methodPath = "//div[contains(@class, 'info-panel')]/descendant::span[text()='" + method + "']";
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			driver.findElement(By.xpath(methodPath));
			Assert.assertTrue(item + " item " + method + " was not hidden initially!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		itemButton.click();
		
		WebElement methodItem = driver.findElement(By.xpath(methodPath));
		Assert.assertTrue(methodItem.isDisplayed());
		itemButton.click();
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			driver.findElement(By.xpath(methodPath));
			Assert.assertTrue(item + " item " + method + " was not hidden!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
	protected void testIncludes() {
		String inheritedItemPath = "//div[contains(@class, 'info-panel')]/descendant::span[text()='changeTextColor']";
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			driver.findElement(By.xpath(inheritedItemPath));
			Assert.assertTrue("Inherited method was not hidden!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		
		String includesPath = "*/qx.ui.toolbar.ToolBar/*/[@label=Includes]";
		Selectable includesButton = (Selectable) driver.findWidget(By.qxh(includesPath));
		includesButton.selectItem("Inherited");		
		WebElement inheritedItem = driver.findElement(By.xpath(inheritedItemPath));
		Assert.assertTrue(inheritedItem.isDisplayed());

		includesButton.selectItem("Inherited");
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			driver.findElement(By.xpath(inheritedItemPath));
			Assert.assertTrue("Inherited method was not hidden!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}

	@Test
	public void classItems() {
		testProperties();
		testIncludes();
		testClassItem("Protected", "_hideResizeLine");
		testClassItem("Private", "__isAtEdge");
		testClassItem("Internal", "getVerticalScrollBarWidth");
	}
}
