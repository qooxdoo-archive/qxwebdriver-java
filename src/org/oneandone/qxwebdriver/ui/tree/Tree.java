package org.oneandone.qxwebdriver.ui.tree;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.form.List;
import org.openqa.selenium.WebElement;

public class Tree extends List implements Selectable, Scrollable {

	public Tree(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

}
