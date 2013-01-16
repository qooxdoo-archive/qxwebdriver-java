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

package org.oneandone.qxwebdriver.ui.tree.core;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class AbstractItem extends org.oneandone.qxwebdriver.ui.core.WidgetImpl {

	public AbstractItem(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	public boolean isOpen() {
		return (Boolean) getPropertyValue("open");
	}

	public void clickOpenCloseButton() {
		Widget button = getChildControl("open");
		if (button != null) {
			button.click();
		}
	}

}
