package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.core.Widget;
import org.openqa.selenium.WebElement;

public class VirtualComboBox extends ComboBox {

	public VirtualComboBox(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected Selectable getList() {
		if (list == null) {
			Widget dropdown = waitForChildControl("dropdown", 3);
			list = (Selectable) dropdown.getChildControl("list");
		}
		return list;
	}

}
