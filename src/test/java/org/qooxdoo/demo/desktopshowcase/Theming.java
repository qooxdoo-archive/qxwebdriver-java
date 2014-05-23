package org.qooxdoo.demo.desktopshowcase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class Theming extends Desktopshowcase {

	public By calculatorLocator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/qx.ui.window.Desktop/showcase.page.theme.calc.view.Calculator");
	
	@Before
	public void setUp() throws Exception {
		selectPage("Theming");
	}
	
	@Test
	public void theming() {
		WebElement calcEl = getRoot().findElement(calculatorLocator);
		Widget calc = driver.getWidgetForElement(calcEl);
		Assert.assertTrue(calc.isDisplayed());
	}
}
