/* ************************************************************************

   qxwebdriver-java

   http://github.com/qooxdoo/qxwebdriver-java

   Copyright:
     2014 1&1 Internet AG, Germany, http://www.1und1.de

   License:
     LGPL: http://www.gnu.org/licenses/lgpl.html
     EPL: http://www.eclipse.org/org/documents/epl-v10.php
     See the license.txt file in the project's top-level directory for details.

   Authors:
     * Daniel Wagner (danielwagner)

************************************************************************ */

package org.oneandone.qxwebdriver.ui.basic;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.core.WidgetImpl;
import org.openqa.selenium.WebElement;

public class Label extends WidgetImpl {

	public Label(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	/**
	 * Returns the string representation of a Label's value
	 * @return label string
	 */
	public String getValue() {
		return (String) executeJavascript("return qx.ui.core.Widget.getWidgetByElement(arguments[0]).getValue().toString()");
	}

}
