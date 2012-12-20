package org.oneandone.qxwebdriver.examples;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.Alert;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HelloWorld {

	/**
	 * A simple demo test for a qx.Desktop skeleton application.
	 * 
	 */
	public static void main(String[] args) {
		QxWebDriver driver = new QxWebDriver(new FirefoxDriver());
		// get waits until the qooxdoo application is ready
		driver.get("http://localhost/custom/source/index.html");
		
		// QxWebDriver.findWidget searches for widgets from the qooxdoo 
		// application root downwards. This locator specifies a Button widget
		// that is a direct child of the root node
		By by = By.qxh("qx.ui.form.Button");
		Widget button = driver.findWidget(by);
		button.click();
		
		Alert alert = driver.switchTo().alert();
		System.out.println("qooxdoo says: " + alert.getText());
		alert.accept();
		
		driver.close();
	}

}
