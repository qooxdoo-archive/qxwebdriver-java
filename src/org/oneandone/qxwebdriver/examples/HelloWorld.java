package org.oneandone.qxwebdriver.examples;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.Alert;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HelloWorld {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		QxWebDriver driver = new QxWebDriver(new FirefoxDriver());
		driver.get("http://localhost/~dwagner/workspace/custom/source/index.html");
		
		By by = By.qxh("qx.ui.form.Button");
		Widget button = driver.findWidget(by);
		button.click();
		
		Alert alert = driver.switchTo().alert();
		System.out.println("qooxdoo says: " + alert.getText());
		alert.accept();
	}

}
