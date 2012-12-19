package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.list.List">List</a>
 * widget
 */
public class VirtualList extends List implements Scrollable, Selectable {

	public VirtualList(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	public Widget getSelectableItem(Integer index) {
		throw new RuntimeException("getSelectableItem(Integer index) is not implemented for qx.ui.list.List, use getSelectableItem(String label) instead.");
	}
	
	public Widget getSelectableItem(String label) {
		scrollTo("y", 0);
		By itemLocator = By.qxh("*/[@label=" + label + "]");
		return scrollToChild("y", itemLocator);
	}

}
