package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class List extends Widget implements Selectable {

	public List(WebElement element, WebDriver webDriver) {
		super(element, webDriver);
	}

	public WebElement getSelectableItem(Integer index) {
		String getter = JavaScript.INSTANCE.getValue("getItemFromSelectables");
		Object result = jsExecutor.executeScript(getter, contentElement, index);
		return (WebElement) result;
	}
	
	public void selectItem(Integer index) {
		getSelectableItem(index).click();
	}
	
	public WebElement getSelectableItem(String label) {
		String getter = JavaScript.INSTANCE.getValue("getItemFromSelectables");
		Object result = jsExecutor.executeScript(getter, contentElement, label);
		return (WebElement) result;
	}
	
	public void selectItem(String label) {
		getSelectableItem(label).click();
	}
}
