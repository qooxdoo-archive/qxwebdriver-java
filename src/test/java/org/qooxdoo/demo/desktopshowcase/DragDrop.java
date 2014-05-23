package org.qooxdoo.demo.desktopshowcase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class DragDrop extends Desktopshowcase {

	public By labelLocator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/qx.ui.container.Composite/[@value=Shop]");
	
	@Before
	public void setUp() throws Exception {
		selectPage("Drag & Drop");
	}
	
	@Test
	public void dragDrop() {
		WebElement labelEl = getRoot().findElement(labelLocator);
		Widget label = driver.getWidgetForElement(labelEl);
		Assert.assertTrue(label.isDisplayed());
	}
}
