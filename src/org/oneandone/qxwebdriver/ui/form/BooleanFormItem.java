package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.BooleanSelectable;
import org.oneandone.qxwebdriver.ui.core.Widget;
import org.openqa.selenium.WebElement;

public class BooleanFormItem extends Widget implements BooleanSelectable {

	public BooleanFormItem(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	public boolean isSelected() {
		return (Boolean) getPropertyValue("value");
	}

}
