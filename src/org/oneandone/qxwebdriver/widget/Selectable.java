package org.oneandone.qxwebdriver.widget;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Selectable extends QxWidget {

	public Selectable(WebElement element, WebDriver webDriver) {
		super(element, webDriver);
	}

	public WebElement getItemFromSelectables(Integer index) {
		String getter = String.format(GET_ITEM_FROM_SELECTABLES, qxHash, index);
		Object result = jsExecutor.executeScript(getter);
		return (WebElement) result;
	}
	
	public WebElement getItemFromSelectables(String label) {
		String getter = String.format(GET_ITEM_FROM_SELECTABLES, qxHash, "'" + label + "'");
		Object result = jsExecutor.executeScript(getter);
		return (WebElement) result;
	}
	
	static protected String GET_ITEM_FROM_SELECTABLES = "var widget = qx.core.ObjectRegistry.fromHashCode('%1$s');" +
		"var selectables = widget.getSelectables();" +
		"for (var i=0; i<selectables.length; i++) {" +
		"	if (selectables[i].getLabel() === %2$s || i === %2$s) {" +
		"		return selectables[i].getContentElement().getDomElement();" +
		"	}" +
		"}" +
		"return null;";
}
