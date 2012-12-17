package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

public class VirtualSelectBox extends SelectBox implements Selectable {

	public VirtualSelectBox(WebElement element, QxWebDriver driver) {
		super(element, driver);
	}
	
	protected Selectable getList() {
		if (list == null) {
			Widget dropdown = getChildControl("dropdown");
			list = (Selectable) dropdown.getChildControl("list");
		}
		return list;
	}
	
	protected void waitForList() {
		waitForChildControl("dropdown", 5);
	}

}
