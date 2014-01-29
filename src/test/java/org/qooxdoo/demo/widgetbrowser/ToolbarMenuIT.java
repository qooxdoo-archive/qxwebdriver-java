package org.qooxdoo.demo.widgetbrowser;

import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;

public class ToolbarMenuIT extends WidgetBrowser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WidgetBrowser.setUpBeforeClass();
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
	}
	
	@Test
	public void splitButton() {
		By splitButtonLocator = By.qxh("*/qx.ui.toolbar.SplitButton");
		Widget tbarSplit = tabPage.findWidget(splitButtonLocator);
		tbarSplit.getChildControl("arrow").click();
		Selectable splitMenu = (Selectable) tbarSplit.getWidgetFromProperty("menu");
		splitMenu.selectItem(0);
	}
	
	@Test
	public void menuButton() {
		By menuButtonLocator = By.qxh("*/[@label=MenuButton]");
		Selectable tbarMenuButton = (Selectable) tabPage.findWidget(menuButtonLocator);
		
		// Must click the button so the menu is rendered before we can check if
		// the MenuRadioButton is selected
		tbarMenuButton.click();
		Selectable buttonMenu = (Selectable) tbarMenuButton.getWidgetFromProperty("menu");
		Widget buttonMenuRadioButton = buttonMenu.getSelectableItem("Menu RadioButton");
		boolean selectedBefore = (Boolean) buttonMenuRadioButton.getPropertyValue("value");
		tbarMenuButton.click();
		tbarMenuButton.selectItem("Menu RadioButton");
		boolean selectedAfter = (Boolean) buttonMenuRadioButton.getPropertyValue("value");
		assertFalse(selectedBefore == selectedAfter);
	}
	
}
