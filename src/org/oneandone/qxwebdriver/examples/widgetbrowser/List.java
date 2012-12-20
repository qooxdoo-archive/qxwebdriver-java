package org.oneandone.qxwebdriver.examples.widgetbrowser;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;

/**
 * Tests for the Widget Browser's Form tab
 *
 */
public class List extends WidgetBrowser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List list = new List();
		list.test();
		System.out.println("All tests done.");
	}
	
	public void test() {
		selectTab("List");
		list();
		virtualList();
	}
	
	public void list() {
		By listLocator = By.qxh("*/qx.ui.form.List"); 
		Widget list = tabPage.findWidget(listLocator);
		// For the regular, non-virtual list we can directly interact with the
		// item since it's already rendered. The tree pane will automatically
		// scroll the item into view when it's focused
		
		Widget item = list.findWidget(By.qxh("*/[@label=Hohl, Hartlieb]"));
		item.click();
		
		// Alternatively, we can use the Selectable interface
		Selectable sList = (Selectable) list;
		sList.selectItem("Prill, Nico");
	}
	
	public void virtualList() {
		// For the virtual list, we must use the Selectable interface. It will
		// scroll the tree until it finds an item with a matching label, then
		// click it.
		By virtualListLocator = By.qxh("*/qx.ui.list.List");
		Selectable vlist = (Selectable) tabPage.findWidget(virtualListLocator);
		vlist.selectItem("Pauls, Gernfried");
		
		By groupedVirtualListLocator = By.qxh("widgetbrowser.pages.List/qx.ui.container.Composite/child[5]");
		Selectable gvlist = (Selectable) tabPage.findWidget(groupedVirtualListLocator);
		gvlist.selectItem("Fritz, Katrein");
	}

}
