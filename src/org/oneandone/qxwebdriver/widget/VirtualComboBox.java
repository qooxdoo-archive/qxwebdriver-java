package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
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
