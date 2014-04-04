package org.oneandone.qxwebdriver.ui.mobile;

import org.oneandone.qxwebdriver.ui.Widget;

/**
 * This interface represents qx.Mobile widgets with selectable child items, e.g.
 * qx.ui.mobile.list.List. For qx.Desktop widgets, please use 
 * org.oneandone.qxwebdriver.ui.Selectable instead.
 *
 */
public interface Selectable extends Widget {
	
	/**
	 * Locates a list item by its title (exact match) and taps it.
	 * @param title The list item's title text
	 */
	public void selectItem(String title);
	
	
	/**
	 * Locates a list item by its position and taps it. 
	 * @param index The list item's index
	 */
	public void selectItem(Integer index);
}
