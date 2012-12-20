package org.oneandone.qxwebdriver;

import java.util.List;

import org.oneandone.qxwebdriver.ui.IWidget;
import org.openqa.selenium.WebElement;

public interface WidgetFactory {

	public IWidget getWidgetForElement(WebElement element, List<String> classes);

}
