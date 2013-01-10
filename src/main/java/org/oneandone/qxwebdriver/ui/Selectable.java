package org.oneandone.qxwebdriver.ui;

import org.oneandone.qxwebdriver.ui.Widget;

/**
 * Represents a widget that allows the user to select one or more out of 
 * several items that are displayed as widgets.
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
