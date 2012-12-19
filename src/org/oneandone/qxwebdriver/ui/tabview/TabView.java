package org.oneandone.qxwebdriver.ui.tabview;

import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.core.Widget;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.tabview.TabView">TabView</a>
 * widget
 */
public class TabView extends Widget implements Selectable {

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
	public Widget getSelectableItem(String label) {
		Widget bar = getChildControl("bar");
		List<Widget> buttons = bar.getChildren();
		Iterator<Widget> iter = buttons.iterator();
		while (iter.hasNext()) {
			Widget button = iter.next();
			String buttonLabel = (String) button.getPropertyValue("label");
			if (buttonLabel.equals(label)) {
				return button;
			}
		}
		return null;
	}

	@Override
	public void selectItem(String label) {
		getSelectableItem(label).click();
	}

}
