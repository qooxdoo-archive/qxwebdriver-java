package org.oneandone.qxwebdriver.ui.mobile.core;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;


public class WidgetImpl extends org.oneandone.qxwebdriver.ui.core.WidgetImpl {

	public WidgetImpl(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
		// workaround for https://github.com/selendroid/selendroid/issues/337
		contentElement = element;
	}

}
