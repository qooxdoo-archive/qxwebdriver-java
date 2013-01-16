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

package org.oneandone.qxwebdriver.ui.menu;

import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.menu.Menu">Menu</a>
 * widget
 */
public class Menu extends org.oneandone.qxwebdriver.ui.core.WidgetImpl implements Selectable {

	//TODO: Nested menus

	public Menu(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	public void selectItem(Integer index) {
		getSelectableItem(index).click();
	}

	@Override
	public void selectItem(String regex) {
		getSelectableItem(regex).click();
	}

	@Override
	public Widget getSelectableItem(Integer index) {
		List<Widget> children = getChildren();
		return children.get(index);
	}

	@Override
	public Widget getSelectableItem(String regex) {
		String locator = "[@label=" + regex + "]";
		return findWidget(org.oneandone.qxwebdriver.By.qxh(locator));
	}

}
