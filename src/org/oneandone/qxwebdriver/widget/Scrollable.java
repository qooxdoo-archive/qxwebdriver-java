package org.oneandone.qxwebdriver.widget;

public interface Scrollable {

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
	public Widget scrollToChild(String direction, org.oneandone.qxwebdriver.By locator);
	
}
