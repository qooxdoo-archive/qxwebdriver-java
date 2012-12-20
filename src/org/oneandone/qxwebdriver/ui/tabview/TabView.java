package org.oneandone.qxwebdriver.ui.tabview;

import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.IWidget;
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
	public IWidget getSelectableItem(Integer index) {
		IWidget bar = getChildControl("bar");
		List<IWidget> buttons = bar.getChildren();
		return buttons.get(index);
	}

	@Override
	public void selectItem(Integer index) {
		getSelectableItem(index).click();
	}

	@Override
	public IWidget getSelectableItem(String regex) {
		IWidget bar = getChildControl("bar");
		List<IWidget> buttons = bar.getChildren();
		Iterator<IWidget> iter = buttons.iterator();
		while (iter.hasNext()) {
			IWidget button = iter.next();
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
