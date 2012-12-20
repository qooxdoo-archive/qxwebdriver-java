package org.oneandone.qxwebdriver.ui;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface WidgetFactory {

	public Widget getWidgetForElement(WebElement element, List<String> classes);

}
