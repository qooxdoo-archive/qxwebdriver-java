package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
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
		waitForList();
		getSelectableItem(index).click();
	}
	
	public Widget getSelectableItem(String label) {
		return getList().getSelectableItem(label);
	}
	
	public void selectItem(String label) {
		getButton().click();
		waitForList();
		getSelectableItem(label).click();
	}
	
	protected Widget getButton() {
		if (button == null) {
			button = driver.getWidgetForElement(contentElement);
		}
		return button;
	}
	
	protected Selectable getList() {
		if (list == null) {
			list = (Selectable) getChildControl("list");
		}
		return list;
	}
	
	//TODO: combine getList and waitForList
	protected void waitForList() {
		waitForChildControl("list", 5);
	}
	
}
