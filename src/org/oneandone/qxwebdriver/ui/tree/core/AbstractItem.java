package org.oneandone.qxwebdriver.ui.tree.core;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.core.Widget;
import org.openqa.selenium.WebElement;

public class AbstractItem extends Widget {

	public AbstractItem(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	public boolean isOpen() {
		return (Boolean) getPropertyValue("open");
	}
	
	public void clickOpenCloseButton() {
		Widget button = getChildControl("open"); 
		if (button != null) {
			button.click(); 
		}
	}

}
