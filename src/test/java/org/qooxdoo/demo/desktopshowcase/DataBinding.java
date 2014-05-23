package org.qooxdoo.demo.desktopshowcase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class DataBinding extends Desktopshowcase {

	public By demoLocator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/qxc.application.datademo.Demo");
	
	@Before
	public void setUp() throws Exception {
		selectPage("Data Binding");
	}
	
	@Test
	public void dataBinding() {
		WebElement demoEl = getRoot().findElement(demoLocator);
		Widget demo = driver.getWidgetForElement(demoEl);
		Assert.assertTrue(demo.isDisplayed());
	}
}
