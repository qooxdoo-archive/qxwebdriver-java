package org.qooxdoo.demo.desktopapiviewer;

import org.junit.Assert;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.tree.core.AbstractItem;

public class Tree extends DesktopApiViewer {

	@Test
	public void tree() {
		selectView("Content");
		
		Selectable tree = (Selectable) driver.findWidget(By.qxh("*/apiviewer.ui.PackageTree"));
		Assert.assertTrue(tree.isDisplayed());
		
		String item1Label = "bom";
		AbstractItem item1 = (AbstractItem) tree.getSelectableItem(item1Label);
		item1.click();
		Assert.assertTrue(item1.isOpen());
		
		String item2Label = "element";
		AbstractItem item2 = (AbstractItem) tree.getSelectableItem(item2Label);
		item2.click();
		Assert.assertTrue(item2.isOpen());

		String item3Label = "Dimension";
		AbstractItem item3 = (AbstractItem) tree.getSelectableItem(item3Label);
		item3.click();
		
		Widget tabButton = driver.findWidget(By.qxh(tabButtonPath));
		Assert.assertEquals("qx.bom.element.Dimension", tabButton.getPropertyValue("label"));
		
		String hash = (String) driver.executeScript("return location.hash");
		Assert.assertEquals("#qx.bom.element.Dimension", hash);
		
	}
}
