package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.IWidget;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.ComboBox">ComboBox</a>
 * widget
 */
public class ComboBox extends SelectBox {

	public ComboBox(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	protected IWidget getButton() {
		if (button == null) {
			button = getChildControl("button");
		}
		return button;
	}
	
	public void sendKeys(CharSequence... keysToSend) {
		getChildControl("textfield").getContentElement().sendKeys(keysToSend);
	}
	
	public void clear() {
		getChildControl("textfield").getContentElement().clear();
	}
	
}
