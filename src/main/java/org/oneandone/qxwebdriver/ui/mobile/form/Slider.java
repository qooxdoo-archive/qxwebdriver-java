package org.oneandone.qxwebdriver.ui.mobile.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Slider extends WidgetImpl implements Widget {

	public Slider(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	public void track(int x, int y, int step) {
		WebElement element = contentElement.findElement(By.xpath("div"));
		track(driver.getWebDriver(), element, x, y, step);
	}

}
