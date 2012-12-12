package org.oneandone.qxwebdriver.widget;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ComboBox extends SelectBox {

	public ComboBox(WebElement element, WebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected WebElement getButton() {
		if (button == null) {
			button = getChildControl("button");
		}
		return button;
	}
	
}
