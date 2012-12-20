package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.IWidget;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.VirtualSelectBox">VirtualSelectBox</a>
 * widget
 */
public class VirtualSelectBox extends SelectBox implements Selectable {

	public VirtualSelectBox(WebElement element, QxWebDriver driver) {
		super(element, driver);
	}
	
	protected Selectable getList() {
		if (list == null) {
			IWidget dropdown = waitForChildControl("dropdown", 3);
			list = (Selectable) dropdown.getChildControl("list");
		}
		return list;
	}

}
