package org.oneandone.qxwebdriver.examples.widgetbrowser;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;

/**
 * Tests for the Widget Browser's Toolbar/Menu tab
 *
 */
public class ToolbarMenu extends WidgetBrowser {

	public static void main(String[] args) {
		ToolbarMenu toolbarMenu = new ToolbarMenu();
		toolbarMenu.test();
		System.out.println("All tests done.");

	}
	
	public void test() {
		selectTab("Toolbar.*");
		menu();
	}
	
	public void menu() {
		By menuLocator = By.qxh("*/qx.ui.menu.Menu");
		Selectable menu = (Selectable) tabPage.findWidget(menuLocator);
		menu.selectItem("Menu RadioButton");
		
		By splitButtonLocator = By.qxh("*/qx.ui.toolbar.SplitButton");
		Widget tbarSplit = tabPage.findWidget(splitButtonLocator);
		tbarSplit.getChildControl("arrow").click();
		Selectable splitMenu = (Selectable) tbarSplit.getWidgetFromProperty("menu");
		splitMenu.selectItem(0);
		
		By menuButtonLocator = By.qxh("*/[@label=MenuButton]");
		Selectable tbarMenuButton = (Selectable) tabPage.findWidget(menuButtonLocator);
		tbarMenuButton.selectItem("Menu RadioButton");
	}

}
