package org.oneandone.qxwebdriver.ui.mobile.list;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.mobile.Selectable;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class List extends WidgetImpl implements Selectable {

	public List(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	public void selectItem(Integer index) {
		index++; //xpath's position() is 1-based
		By locator = By.xpath("descendant::li[contains(@class, 'list-item') and position() = " + index + "]");
		WebElement item = contentElement.findElement(locator);
		WidgetImpl.tap(driver.getWebDriver(), item);
	}

	public void selectItem(String title) {
		By locator = By.xpath("descendant::div[contains(@class, 'list-item-title') and text() = '" + title + "']/ancestor::li");
		WebElement item = contentElement.findElement(locator);
		WidgetImpl.tap(driver.getWebDriver(), item);
	}

}
