package org.oneandone.qxwebdriver.widget;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Menu extends QxWidget {

	public Menu(WebElement element, WebDriver webDriver) {
		super(element, webDriver);
	}
	
	public void selectItem(Integer index) {
		List<WebElement> children = getChildren();
		children.get(index).click();
	}

}
