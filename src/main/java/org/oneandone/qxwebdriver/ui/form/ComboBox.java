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

package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.ComboBox">ComboBox</a>
 * widget
 */
public class ComboBox extends SelectBox {

	public ComboBox(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	protected Widget getButton() {
		if (button == null) {
			button = getChildControl("button");
		}
		return button;
	}

	public void sendKeys(CharSequence... keysToSend) {
		getChildControl("textfield").getContentElement().sendKeys(keysToSend);
	}

	public void clear() {
		getChildControl("textfield").getContentElement().clear();
	}

}
