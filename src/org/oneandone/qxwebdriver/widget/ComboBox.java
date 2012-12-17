package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

public class ComboBox extends SelectBox {

	public ComboBox(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected Widget getButton() {
		if (button == null) {
			button = getChildControl("button");
		}
		return button;
	}
	
}
