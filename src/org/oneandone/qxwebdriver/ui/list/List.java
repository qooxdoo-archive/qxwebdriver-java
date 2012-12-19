package org.oneandone.qxwebdriver.ui.list;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.core.Widget;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.list.List">List</a>
 * widget
 */
public class List extends org.oneandone.qxwebdriver.ui.form.List 
implements Scrollable, Selectable {

	public List(WebElement element, QxWebDriver webDriver) {
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
