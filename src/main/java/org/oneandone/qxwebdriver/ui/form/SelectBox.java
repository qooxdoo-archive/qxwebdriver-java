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

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.SelectBox">SelectBox</a>
 * widget
 */
public class SelectBox extends org.oneandone.qxwebdriver.ui.core.WidgetImpl implements Selectable {

	public SelectBox(WebElement element, QxWebDriver driver) {
		super(element, driver);
	}

	protected Widget button = null;
	protected Selectable list = null;

	public Widget getSelectableItem(Integer index) {
		return getList().getSelectableItem(index);
	}

	public void selectItem(Integer index) {
		getButton().click();
		getSelectableItem(index).click();
	}

	public Widget getSelectableItem(String regex) {
		return getList().getSelectableItem(regex);
	}

	public void selectItem(String regex) {
		getButton().click();
		getSelectableItem(regex).click();
	}

	protected Widget getButton() {
		if (button == null) {
			button = driver.getWidgetForElement(contentElement);
		}
		return button;
	}

	protected Selectable getList() {
		if (list == null) {
			list = (Selectable) waitForChildControl("list", 3);
		}
		return list;
	}

}
