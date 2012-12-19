package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class TreeItem extends Widget {

	public TreeItem(WebElement element, QxWebDriver webDriver) {
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
