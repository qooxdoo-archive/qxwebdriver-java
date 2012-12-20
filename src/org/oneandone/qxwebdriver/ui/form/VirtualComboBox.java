package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.IWidget;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.openqa.selenium.WebElement;

public class VirtualComboBox extends ComboBox {

	public VirtualComboBox(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected Selectable getList() {
		if (list == null) {
			IWidget dropdown = waitForChildControl("dropdown", 3);
			list = (Selectable) dropdown.getChildControl("list");
		}
		return list;
	}

}
