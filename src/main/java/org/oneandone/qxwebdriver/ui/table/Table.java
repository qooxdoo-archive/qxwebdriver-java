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

package org.oneandone.qxwebdriver.ui.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.core.WidgetImpl;
import org.openqa.selenium.WebElement;

public class Table extends WidgetImpl implements Scrollable {

	public Table(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	public List<String> getHeaderLabels() {
		Widget header = findWidget(By.qxh("*/qx.ui.table.pane.Header"));
		List<WebElement> children = header
				.getContentElement()
				.findElements(
						By.xpath("div[starts-with(@class, 'qx-table-header-cell')]/div[not(contains(@style, 'background-image'))]"));

		List<String> labels = new ArrayList<String>();

		Iterator<WebElement> itr = children.iterator();
		while (itr.hasNext()) {
			WebElement child = (WebElement) itr.next();
			labels.add(child.getText());
		}

		return labels;
	}

	public Scrollable getScroller() {
		return (Scrollable) findWidget(By.qxh("*/qx.ui.table.pane.Scroller"));
	}

	@Override
	public void scrollTo(String direction, Integer position) {
		getScroller().scrollTo(direction, position);
	}

	@Override
	public Widget scrollToChild(String direction, org.openqa.selenium.By locator) {
		return getScroller().scrollToChild(direction, locator);
	}

	@Override
	public Long getMaximum(String direction) {
		return getScroller().getMaximum(direction);
	}

	@Override
	public Long getScrollPosition(String direction) {
		return getScroller().getScrollPosition(direction);
	}

}
