package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

public class BooleanFormItem extends Widget implements BooleanSelectable {

	public BooleanFormItem(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	public boolean isSelected() {
		return (Boolean) getPropertyValue("value");
	}

}
