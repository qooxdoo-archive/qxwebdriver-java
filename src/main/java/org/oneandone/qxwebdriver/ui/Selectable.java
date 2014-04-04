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


/**
 * Represents a widget that allows the user to select one or more out of
 * several items that are displayed as widgets. Only works with qx.Desktop widgets,
 * for qx.Mobile please use org.oneandone.qxwebdriver.ui.mobile.Selectable instead.
 *
 */
public interface Selectable extends Widget {

	/**
	 * Finds a selectable child widget by index and returns it
	 */
	public Widget getSelectableItem(Integer index);

	/**
	 * Finds a selectable child widget by index and selects it
	 */
	public void selectItem(Integer index);

	/**
	 * Finds the first selectable child widget with a label matching the regular
	 * expression and returns it
	 */
	public Widget getSelectableItem(String regex);

	/**
	 * Finds the first selectable child widget with a label matching the regular
	 * expression and selects it
	 */
	public void selectItem(String regex);

}
