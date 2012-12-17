package org.oneandone.qxwebdriver.widget;

import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

public class TabView extends Widget implements Selectable {

	public TabView(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	//TODO: Implement Scrollable

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
