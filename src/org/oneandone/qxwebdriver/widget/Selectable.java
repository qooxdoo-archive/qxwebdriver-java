package org.oneandone.qxwebdriver.widget;

public interface Selectable {

	public Widget getSelectableItem(Integer index);
	
	public void selectItem(Integer index);
	
	public Widget getSelectableItem(String label);
	
	public void selectItem(String label);
	
}
