package org.oneandone.qxwebdriver.widget;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectBox extends QxWidget {

	public SelectBox(WebElement element, WebDriver driver) {
		super(element, driver);
	}
	
	protected WebElement button = null;
	protected Selectable list = null;
	
	public void selectItem(Integer index) {
		getButton().click();
		waitForChildControl("list", 5);
		WebElement item = getList().getItemFromSelectables(index);
		item.click();
	}
	
	public void selectItem(String label) {
		getButton().click();
		waitForChildControl("list", 5);
		WebElement item = getList().getItemFromSelectables(label);
		item.click();
	}
	
	protected WebElement getButton() {
		if (button == null) {
			button = contentElement;
		}
		return button;
	}
	
	protected Selectable getList() {
		if (list == null) {
			WebElement listElement = getChildControl("list");
			list = new Selectable(listElement, driver);
		}
		return list;
	}
	
}
