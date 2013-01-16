/* ************************************************************************

   qxwebdriver-java

   http://github.com/qooxdoo/qxwebdriver-java

   Copyright:
     2012-2013 1&1 Internet AG, Germany, http://www.1und1.de

   License:
     LGPL: http://www.gnu.org/licenses/lgpl.html
     EPL: http://www.eclipse.org/org/documents/epl-v10.php
     See the license.txt file in the project's top-level directory for details.

   Authors:
     * Daniel Wagner (danielwagner)

************************************************************************ */

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
