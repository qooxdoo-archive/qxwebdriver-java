package org.qooxdoo.demo.desktopapiviewer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Tabs extends DesktopApiViewer {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DesktopApiViewer.setUpBeforeClass();
		String className = "qx.ui.form.Button";
		selectClass(className);
	}

	@Test
	public void tabs() {
		String newTabClass = "qx.ui.form.MenuButton";
		WebElement link = driver.findElement(By.xpath("//a[text()='" + newTabClass + "']"));
		Actions action = new Actions(driver.getWebDriver());
		action.keyDown(Keys.SHIFT);
		action.click(link);
		action.keyUp(Keys.SHIFT);
		action.perform();
		
		String newTabPath = "*/apiviewer.DetailFrameTabView/*/[@label=" + newTabClass + "]";
		Widget newTabButton = driver.findWidget(By.qxh(newTabPath));
		Assert.assertTrue(newTabButton.isDisplayed());
		
		String closeButtonPath = newTabPath + "/qx.ui.form.Button";
		Widget closeButton = driver.findWidget(By.qxh(closeButtonPath));
		Assert.assertTrue(closeButton.isDisplayed());
		closeButton.click();
		
		try {
			driver.findWidget(By.qxh(newTabPath));
			Assert.assertTrue("New tab was not closed!", false);
		} catch(NoSuchElementException e) {}
	}
	
}
