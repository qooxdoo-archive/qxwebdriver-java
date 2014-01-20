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

package org.oneandone.qxwebdriver.ui;

public interface Scrollable extends Widget {

	/**
	 * Scrolls the widget to a given position
	 *
	 * @param direction "x" or "y" for horizontal/vertical scrolling
	 * @param position Position (in pixels) to scroll to
	 */
	public void scrollTo(String direction, Integer position);

	/**
	 * Scrolls the area in the given direction until the locator finds a child
	 * widget. The locator will be executed in the scroll area's context, so
	 * a relative locator should be used, e.g. <code>By.qxh("*\/[@label=Foo]")</code>
	 *
	 * @param direction "x" or "y" for horizontal/vertical scrolling
	 * @param locator Child widget locator
	 * @return The matching child widget
	 */
	public Widget scrollToChild(String direction, org.openqa.selenium.By locator);

	/**
	 * Returns the maximum scroll position of the widget
	 *
	 * @param direction "x" or "y" for horizontal/vertical maximum
	 * @return maximum scroll position in pixels
	 */
	public Long getMaximum(String direction);

	/**
	 * Returns the current scroll position of the widget
	 *
	 * @param direction "x" or "y" for horizontal/vertical position
	 * @return scroll position in pixels
	 */
	public Long getScrollPosition(String direction);

}
