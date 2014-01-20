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

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.core.scroll.ScrollPane;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.menu.Menu">Menu</a>
 * widget
 */
public class Menu extends org.oneandone.qxwebdriver.ui.core.WidgetImpl implements Selectable, Scrollable {

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
		Boolean hasSlideBar = hasChildControl("slidebar");
		if (hasSlideBar) {
			System.err.println("Menu item selection by index is currently " + 
							"only supported for non-scrolling menus!");
			return null;
		}
		else {
			List<Widget> children = getChildren();
			return children.get(index);
		}
	}

	@Override
	public Widget getSelectableItem(String label) {
		By itemLocator = By.qxh("*/[@label=" + label + "]");
		Boolean hasSlideBar = hasChildControl("slidebar");
		if (hasSlideBar) {
			scrollTo("y", 0);
			return scrollToChild("y", itemLocator);
		}
		else {
			return findWidget(itemLocator);
		}
	}

	public ScrollPane getScrollPane() {
		Widget slideBar = getChildControl("slidebar");
		return (ScrollPane) slideBar.getChildControl("scrollpane");
	}
	
	@Override
	public void scrollTo(String direction, Integer position) {
		ScrollPane scrollPane = getScrollPane();
		scrollPane.scrollTo(direction, position);
		
	}

	@Override
	public Widget scrollToChild(String direction, org.openqa.selenium.By locator) {
		ScrollPane scrollPane = getScrollPane();
		return scrollPane.scrollToChild(direction, locator);
	}

	@Override
	public Long getMaximum(String direction) {
		ScrollPane scrollPane = getScrollPane();
		return scrollPane.getMaximum(direction);
	}

	@Override
	public Long getScrollPosition(String direction) {
		ScrollPane scrollPane = getScrollPane();
		return scrollPane.getScrollPosition(direction);
	}

}
