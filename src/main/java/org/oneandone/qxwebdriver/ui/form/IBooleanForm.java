package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

public class IBooleanForm extends org.oneandone.qxwebdriver.ui.core.WidgetImpl {

	public IBooleanForm(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	public boolean isSelected() {
		return (Boolean) getPropertyValue("value");
	}

}
