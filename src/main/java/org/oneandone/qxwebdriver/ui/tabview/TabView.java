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

package org.oneandone.qxwebdriver.ui.tabview;

import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.tabview.TabView">TabView</a>
 * widget
 */
public class TabView extends org.oneandone.qxwebdriver.ui.core.WidgetImpl implements Selectable {

	public TabView(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	@Override
	public Widget getSelectableItem(Integer index) {
		Widget bar = getChildControl("bar");
		List<Widget> buttons = bar.getChildren();
		return buttons.get(index);
	}

	@Override
	public void selectItem(Integer index) {
		getSelectableItem(index).click();
	}

	@Override
	public Widget getSelectableItem(String regex) {
		Widget bar = getChildControl("bar");
		List<Widget> buttons = bar.getChildren();
		Iterator<Widget> iter = buttons.iterator();
		while (iter.hasNext()) {
			Widget button = iter.next();
			String buttonLabel = (String) button.getPropertyValue("label");
			if (buttonLabel.matches(regex)) {
				return button;
			}
		}
		return null;
	}

	@Override
	public void selectItem(String regex) {
		getSelectableItem(regex).click();
	}

}
