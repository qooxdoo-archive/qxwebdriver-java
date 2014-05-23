package org.qooxdoo.demo.desktopshowcase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class Form extends Desktopshowcase {
	
	public By formLocator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/qxc.application.formdemo.FormItems");
	
	@Before
	public void setUp() throws Exception {
		selectPage("Form");
	}
	
	@Test
	public void form() {
		WebElement formEl = getRoot().findElement(formLocator);
		Widget form = driver.getWidgetForElement(formEl);
		Assert.assertTrue(form.isDisplayed());
	}
}
