package org.oneandone.qxwebdriver.ui;

import org.oneandone.qxwebdriver.ui.IWidget;

/**
 * Represents a widget that allows the user to select one or more out of 
 * several items that are displayed as widgets.
 *
 */
public interface Selectable extends IWidget {

	/**
	 * Finds a selectable child widget by index and returns it
	 */
	public IWidget getSelectableItem(Integer index);
	
	/**
	 * Finds a selectable child widget by index and selects it
	 */
	public void selectItem(Integer index);
	
	/**
	 * Finds the first selectable child widget with a label matching the regular
	 * expression and returns it
	 */
	public IWidget getSelectableItem(String regex);
	
	/**
	 * Finds the first selectable child widget with a label matching the regular
	 * expression and selects it
	 */
	public void selectItem(String label);
	
}
