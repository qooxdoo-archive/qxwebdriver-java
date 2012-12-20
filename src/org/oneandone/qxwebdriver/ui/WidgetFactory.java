package org.oneandone.qxwebdriver.ui;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface WidgetFactory {

	public IWidget getWidgetForElement(WebElement element, List<String> classes);

}
