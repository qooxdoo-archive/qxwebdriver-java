package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.openqa.selenium.WebElement;

public class List extends ScrollArea implements Selectable, Scrollable {

	public List(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	public Widget getSelectableItem(Integer index) {
		String getter = JavaScript.INSTANCE.getValue("getItemFromSelectables");
		Object result = jsExecutor.executeScript(getter, contentElement, index);
		WebElement element = (WebElement) result;
		return driver.getWidgetForElement(element);
	}
	
	public void selectItem(Integer index) {
		getSelectableItem(index).click();
	}
	
	public Widget getSelectableItem(String label) {
		String getter = JavaScript.INSTANCE.getValue("getItemFromSelectables");
		Object result = jsExecutor.executeScript(getter, contentElement, label);
		WebElement element = (WebElement) result;
		return driver.getWidgetForElement(element);
	}
	
	public void selectItem(String label) {
		getSelectableItem(label).click();
	}
}
