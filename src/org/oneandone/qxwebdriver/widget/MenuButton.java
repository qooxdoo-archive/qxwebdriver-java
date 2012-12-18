package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

public class MenuButton extends SelectBox implements Selectable {

	public MenuButton(WebElement element, QxWebDriver driver) {
		super(element, driver);
	}
	
	protected Selectable getList() {
		if (list == null) {
			list = (Selectable) getWidgetFromProperty("menu");
		}
		return list;
	}
	
	protected Widget getButton() {
		return this;
	}

}
