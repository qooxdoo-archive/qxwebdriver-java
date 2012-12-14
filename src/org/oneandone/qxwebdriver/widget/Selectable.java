package org.oneandone.qxwebdriver.widget;

import org.openqa.selenium.WebElement;

public interface Selectable {

	public WebElement getSelectableItem(Integer index);
	
	public void selectItem(Integer index);
	
	public WebElement getSelectableItem(String label);
	
	public void selectItem(String label);
	
}
