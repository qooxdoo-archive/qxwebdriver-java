package org.oneandone.qxwebdriver.ui.menu;

import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.core.Widget;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.menu.Menu">Menu</a>
 * widget
 */
public class Menu extends Widget implements Selectable {
	
	//TODO: Nested menus

	public Menu(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	public void selectItem(Integer index) {
		getSelectableItem(index).click();
	}
	
	@Override
	public void selectItem(String label) {
		getSelectableItem(label).click();
	}

	@Override
	public Widget getSelectableItem(Integer index) {
		List<Widget> children = getChildren();
		return children.get(index);
	}

	@Override
	public Widget getSelectableItem(String label) {
		String locator = "[@label=" + label + "]";
		return findWidget(org.oneandone.qxwebdriver.By.qxh(locator));
	}

}
