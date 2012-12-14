package org.oneandone.qxwebdriver.widget;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectBox extends Widget implements Selectable {

	public SelectBox(WebElement element, WebDriver driver) {
		super(element, driver);
	}
	
	protected WebElement button = null;
	protected Selectable list = null;
	
	public WebElement getSelectableItem(Integer index) {
		return getList().getSelectableItem(index);
	}
	
	public void selectItem(Integer index) {
		getButton().click();
		waitForChildControl("list", 5);
		getSelectableItem(index).click();
	}
	
	public WebElement getSelectableItem(String label) {
		return getList().getSelectableItem(label);
	}
	
	public void selectItem(String label) {
		getButton().click();
		waitForChildControl("list", 5);
		getSelectableItem(label).click();
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
			list = new List(listElement, driver);
		}
		return list;
	}
	
}
