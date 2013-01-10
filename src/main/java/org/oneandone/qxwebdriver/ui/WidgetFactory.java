package org.oneandone.qxwebdriver.ui;

import org.openqa.selenium.WebElement;

public interface WidgetFactory {

	public Widget getWidgetForElement(WebElement element);

}
