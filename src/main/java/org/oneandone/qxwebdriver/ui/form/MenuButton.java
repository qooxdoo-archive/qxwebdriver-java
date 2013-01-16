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
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class MenuButton extends SelectBox implements Selectable {

	public MenuButton(WebElement element, QxWebDriver driver) {
		super(element, driver);
	}

	protected Selectable getList() {
		if (list == null) {
			list = (Selectable) getWidgetFromProperty("menu");
		}
		return list;
	}

	protected Widget getButton() {
		return this;
	}

}
