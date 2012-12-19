package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.core.Widget;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.SelectBox">SelectBox</a>
 * widget
 */
public class SelectBox extends Widget implements Selectable {

	public SelectBox(WebElement element, QxWebDriver driver) {
		super(element, driver);
	}
	
	protected Widget button = null;
	protected Selectable list = null;
	
	public Widget getSelectableItem(Integer index) {
		return getList().getSelectableItem(index);
	}
	
	public void selectItem(Integer index) {
		getButton().click();
		getList();
		getSelectableItem(index).click();
	}
	
	public Widget getSelectableItem(String regex) {
		return getList().getSelectableItem(regex);
	}
	
	public void selectItem(String regex) {
		getButton().click();
		getList();
		getSelectableItem(regex).click();
	}
	
	protected Widget getButton() {
		if (button == null) {
			button = driver.getWidgetForElement(contentElement);
		}
		return button;
	}
	
	protected Selectable getList() {
		if (list == null) {
			list = (Selectable) waitForChildControl("list", 3);
		}
		return list;
	}
	
}
