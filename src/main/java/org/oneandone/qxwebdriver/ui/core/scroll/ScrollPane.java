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

package org.oneandone.qxwebdriver.ui.core.scroll;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.openqa.selenium.WebElement;

public class ScrollPane extends AbstractScrollArea implements Scrollable {

	public ScrollPane(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	@Override
	public void scrollTo(String direction, Integer position) {
		jsRunner.runScript("scrollTo", contentElement, position, direction);
	}

	public Long getMaximum(String direction) {
		return (Long) jsRunner.runScript("getScrollMax", contentElement, direction);
	}

	public Long getScrollPosition(String direction) {
		String propertyName = "scroll" + direction.toUpperCase();
		return (Long) getPropertyValue(propertyName);
	}

	public Long getScrollStep(String direction) {
		return (long) 10;
	}
}
