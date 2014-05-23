package org.qooxdoo.demo.desktopshowcase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class Tree extends Desktopshowcase {
	
	public By treeLocator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/qx.ui.window.Desktop/qx.ui.window.Window/qx.ui.tree.Tree");
	
	@Before
	public void setUp() throws Exception {
		selectPage("Tree");
	}
	
	@Test
	public void tree() {
		WebElement treeEl = getRoot().findElement(treeLocator);
		Widget tree = driver.getWidgetForElement(treeEl);
		Assert.assertTrue(tree.isDisplayed());
	}
}
