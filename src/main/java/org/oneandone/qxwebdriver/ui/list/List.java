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

package org.oneandone.qxwebdriver.ui.list;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.list.List">List</a>
 * widget
 */
public class List extends org.oneandone.qxwebdriver.ui.form.List
implements Scrollable, Selectable {

	public List(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	public Widget getSelectableItem(Integer index) {
		throw new RuntimeException("getSelectableItem(Integer index) is not implemented for qx.ui.list.List, use getSelectableItem(String label) instead.");
	}

	public Widget getSelectableItem(String label) {
		scrollTo("y", 0);
		By itemLocator = By.qxh("*/[@label=" + label + "]");
		return scrollToChild("y", itemLocator);
	}

}
