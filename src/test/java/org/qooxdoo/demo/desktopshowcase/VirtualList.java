package org.qooxdoo.demo.desktopshowcase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class VirtualList extends Desktopshowcase {
	
	public By rosterLocator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/qx.ui.window.Desktop/qx.ui.window.Window/showcase.page.virtuallist.messenger.Roster");
	
	@Before
	public void setUp() throws Exception {
		selectPage("Virtual List");
	}
	
	@Test
	public void virtualList() {
		WebElement rosterEl = getRoot().findElement(rosterLocator);
		Widget roster = driver.getWidgetForElement(rosterEl);
		Assert.assertTrue(roster.isDisplayed());
	}
}
