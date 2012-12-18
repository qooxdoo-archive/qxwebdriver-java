package org.oneandone.qxwebdriver.widget;

import org.openqa.selenium.WebElement;

/**
 * Represents a widget that allows the user to select one or more out of 
 * several items that are displayed as widgets.
 *
 */
public interface Selectable extends WebElement {

	/**
	 * Finds a selectable child widget by index and returns it
	 */
	public Widget getSelectableItem(Integer index);
	
	/**
	 * Finds a selectable child widget by index and selects it
	 */
	public void selectItem(Integer index);
	
	/**
	 * Finds a selectable child widget by the label text and returns it
	 */
	public Widget getSelectableItem(String label);
	
	/**
	 * Finds a selectable child widget by the label text and selects it
	 */
	public void selectItem(String label);
	
}
