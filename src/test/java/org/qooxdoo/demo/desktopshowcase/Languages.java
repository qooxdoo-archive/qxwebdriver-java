package org.qooxdoo.demo.desktopshowcase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class Languages extends Desktopshowcase {

	public By buttonGroupLocator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/qx.ui.container.Composite/qx.ui.form.RadioButtonGroup");
	
	@Before
	public void setUp() throws Exception {
		selectPage("Languages");
	}
	
	@Test
	public void languages() {
		WebElement buttonGroupEl = getRoot().findElement(buttonGroupLocator);
		Widget buttonGroup = driver.getWidgetForElement(buttonGroupEl);
		Assert.assertTrue(buttonGroup.isDisplayed());
	}
}
