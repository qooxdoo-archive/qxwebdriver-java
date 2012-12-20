package org.oneandone.qxwebdriver.examples.widgetbrowser;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.tree.core.AbstractItem;

/**
 * Tests for the Widget Browser's Tree tab
 *
 */
public class Tree extends WidgetBrowser {

	public static void main(String[] args) {
		Tree tree = new Tree();
		tree.test();
		System.out.println("All tests done.");
	}
	
	public void test() {
		selectTab("Tree");
		tree();
		virtualTree();
	}
	
	protected void treeTestCommon(String treeLocator) {
		Selectable tree = (Selectable) tabPage.findWidget(By.qxh(treeLocator));
		
		//By item1Locator = By.qxh("*/[@label=" + item1LabelExpected + "]");
		//TreeItem item1 = (TreeItem) tree.findWidget(item1Locator);
		
		String item1LabelExpected = "Desktop";
		AbstractItem item1 = (AbstractItem) tree.getSelectableItem(item1LabelExpected);
		
		item1.click();
		if (item1.isOpen()) {
			item1.clickOpenCloseButton();
		}
		
		String item2LabelExpected = "Inbox";
		AbstractItem item2 = (AbstractItem) tree.getSelectableItem(item2LabelExpected);
		if (!item2.isOpen()) {
			item2.clickOpenCloseButton();
		}
		
		String item3LabelExpected = "Trash";
		AbstractItem item3 = (AbstractItem) tree.getSelectableItem(item3LabelExpected);
		item3.clickOpenCloseButton();
		
		String item4LabelExpected = "Junk #12";
		tree.selectItem(item4LabelExpected);
	}
	
	public void tree() {
		treeTestCommon("*/qx.ui.tree.Tree");
	}
	
	public void virtualTree() {
		treeTestCommon("*/qx.ui.tree.VirtualTree");
	}

}
