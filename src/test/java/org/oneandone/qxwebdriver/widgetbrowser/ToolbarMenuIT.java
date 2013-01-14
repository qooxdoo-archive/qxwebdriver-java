package org.oneandone.qxwebdriver.widgetbrowser;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;

public class ToolbarMenuIT extends Common {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.setUpBeforeClass();
		selectTab("Toolbar.*");
	}
	
	@Test
	public void menu() {
		By menuLocator = By.qxh("*/qx.ui.menu.Menu");
		Selectable menu = (Selectable) tabPage.findWidget(menuLocator);
		Widget menuRadioButton = menu.getSelectableItem("Menu RadioButton");
		boolean selectedBefore = (Boolean) menuRadioButton.getPropertyValue("value");
		menu.selectItem("Menu RadioButton");
		boolean selectedAfter = (Boolean) menuRadioButton.getPropertyValue("value");
		assertFalse(selectedBefore == selectedAfter);
		
		By splitButtonLocator = By.qxh("*/qx.ui.toolbar.SplitButton");
		Widget tbarSplit = tabPage.findWidget(splitButtonLocator);
		tbarSplit.getChildControl("arrow").click();
		Selectable splitMenu = (Selectable) tbarSplit.getWidgetFromProperty("menu");
		splitMenu.selectItem(0);
		
		By menuButtonLocator = By.qxh("*/[@label=MenuButton]");
		Selectable tbarMenuButton = (Selectable) tabPage.findWidget(menuButtonLocator);
		
		// Must click the button so the menu is rendered before we can check if
		// the MenuRadioButton is selected
		tbarMenuButton.click();
		Selectable buttonMenu = (Selectable) tbarMenuButton.getWidgetFromProperty("menu");
		Widget buttonMenuRadioButton = buttonMenu.getSelectableItem("Menu RadioButton");
		selectedBefore = (Boolean) buttonMenuRadioButton.getPropertyValue("value");
		tbarMenuButton.click();
		tbarMenuButton.selectItem("Menu RadioButton");
		selectedAfter = (Boolean) buttonMenuRadioButton.getPropertyValue("value");
		assertFalse(selectedBefore == selectedAfter);
	}

}
