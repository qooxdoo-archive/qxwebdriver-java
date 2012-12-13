package org.oneandone.qxwebdriver.widget;

import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Selectable extends QxWidget {

	public Selectable(WebElement element, WebDriver webDriver) {
		super(element, webDriver);
	}

	public WebElement getItemFromSelectables(Integer index) {
		String getter = JavaScript.INSTANCE.getValue("getItemFromSelectables");
		Object result = jsExecutor.executeScript(getter, contentElement, index);
		return (WebElement) result;
	}
	
	public WebElement getItemFromSelectables(String label) {
		String getter = JavaScript.INSTANCE.getValue("getItemFromSelectables");
		Object result = jsExecutor.executeScript(getter, contentElement, label);
		return (WebElement) result;
	}
}
