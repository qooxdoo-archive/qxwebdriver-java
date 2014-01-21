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

package org.oneandone.qxwebdriver.ui.table.pane;

import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.core.scroll.AbstractScrollArea;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Scroller extends AbstractScrollArea implements Scrollable {

	public Scroller(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	@Override
	public void scrollTo(String direction, Integer position) {
		String propertyName = "scroll" + direction.toUpperCase();
		jsRunner.runScript("setPropertyValue", contentElement, propertyName,
				position);

	}

	@Override
	public Long getMaximum(String direction) {
		if (direction == "y") {
			return (Long) jsRunner.runScript("getTableScrollerMaximum", contentElement);
		} else {
			// TODO
			return new Long(0);
		}
	}

	@Override
	public Long getScrollPosition(String direction) {
		String propertyName = "scroll" + direction.toUpperCase();
		return (Long) jsRunner.runScript("getPropertyValue", contentElement, propertyName);
	}

	@Override
	public Long getScrollStep(String direction) {
		if (direction == "y") {
			return (Long) jsRunner.runScript("getTableRowHeight", contentElement);
		} else {
			// TODO
			return new Long(0);
		}
	}
	
	public WebElement getVisibleRow(Integer index) {
		Widget pane = getChildControl("pane");
		List<WebElement> rows = pane.getContentElement().findElements(By.xpath("div/div"));
		if (index <= rows.size()) {
			return rows.get(index);
		}
		return null;
	}
	
	public WebElement scrollToRow(Integer rowIndex) {
		Long firstVisibleRow = getFirstVisibleRow();
		Long visibleRowCount = getVisibleRowCount();
		Long lastVisibleRow = firstVisibleRow + visibleRowCount - 1;
		
		if (rowIndex.longValue() >= firstVisibleRow && rowIndex.longValue() <= lastVisibleRow) {
			Integer visibleIndex = (int) (rowIndex.longValue() - firstVisibleRow);
			return getVisibleRow(visibleIndex);
		}
		
		String direction = "y";
		Long singleStep = getScrollStep(direction);
		Long scrollPosition = getScrollPosition(direction);
		Long maximum = getMaximum(direction);
		
		if (rowIndex.longValue() > firstVisibleRow && scrollPosition < maximum) {
			int to = (int) (scrollPosition + singleStep);
			scrollTo(direction, to);
			return scrollToRow(rowIndex);
		} else if (rowIndex.longValue() < lastVisibleRow && scrollPosition > 0) {
			int to = (int) (scrollPosition - singleStep);
			scrollTo(direction, to);
			return scrollToRow(rowIndex);
		}
		
		return null;
	}
	
	public Long getFirstVisibleRow() {
		return (Long) jsRunner.runScript("getFirstVisibleTableRow", contentElement);
	}
	
	public Long getVisibleRowCount() {
		return (Long) jsRunner.runScript("getVisibleTableRowCount", contentElement);
	}

}
