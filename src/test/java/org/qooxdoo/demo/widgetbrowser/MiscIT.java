package org.qooxdoo.demo.widgetbrowser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;

public class MiscIT extends Common {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.setUpBeforeClass();
		selectTab("Misc");
	}

	@Test
	public void dragDrop() {
		By parentLocator = By
				.qxh("*/[@classname=widgetbrowser.pages.Misc]/qx.ui.container.Composite/child[9]");
		Widget parentContainer = tabPage.findWidget(parentLocator);

		By sourceLocator = By.qxh("child[0]");
		Selectable dragFrom = (Selectable) parentContainer
				.findWidget(sourceLocator);

		By targetLocator = By.qxh("child[1]");
		Selectable dragTo = (Selectable) parentContainer
				.findWidget(targetLocator);

		// get an item from the source list
		String label = "Item 4";
		Widget item = dragFrom.getSelectableItem(label);

		// drag the item to the target list
		item.dragToWidget(dragTo);

		// check if the item was removed from the source
		assertNull(dragFrom.getSelectableItem(label));

		// check if the item was added to the target
		assertNotNull(dragTo.getSelectableItem(label));
	}

}
