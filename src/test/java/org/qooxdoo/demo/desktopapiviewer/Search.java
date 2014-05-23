package org.qooxdoo.demo.desktopapiviewer;

import org.junit.Assert;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.table.Table;

public class Search extends DesktopApiViewer {

	@Test
	public void searchApi() throws InterruptedException {
		selectView("Search");
		
		String tablePath = "*/apiviewer.ui.SearchView/*/qx.ui.table.Table";
		Table table = (Table) driver.findWidget(By.qxh(tablePath));
		Long initialRowCount = (Long) table.getRowCount();
		Assert.assertEquals(Double.valueOf(0), Double.valueOf(initialRowCount));
		
		typeInSearch("window");
		Thread.sleep(500);
		Long resultRowCount = (Long) table.getRowCount();
		Assert.assertTrue(resultRowCount > 0);
		
		// the namespace filter isn't applied in FF/Chrome on Linux while the screen is turned off
		if (!System.getProperty("org.qooxdoo.demo.platform").equals("linux")) {
			String namespaceFieldPath = "*/apiviewer.ui.SearchView/qx.ui.container.Composite/child[3]";
			Widget namespaceField = driver.findWidget(By.qxh(namespaceFieldPath));
			namespaceField.sendKeys("window");
			
			// Focus the searchField again to make sure the namespace filter is applied
			String searchFieldPath = "*/apiviewer.ui.SearchView/*/qx.ui.form.TextField";
			Widget searchField = driver.findWidget(By.qxh(searchFieldPath));
			searchField.click();
			Thread.sleep(500);
	
			Long filteredRowCount = (Long) table.getRowCount();
			String msg = "Namespace filter failed: " + resultRowCount + " before, " + filteredRowCount + " after adding filter.";
			Assert.assertTrue(msg, filteredRowCount < resultRowCount);
		}
		
		Widget toggleButton = driver.findWidget(By.qxh("*/apiviewer.ui.SearchView/*/[@label=Toggle Filters]"));
		toggleButton.click();
		Thread.sleep(500);
		Long toggledCount = (Long) table.getRowCount();
		Assert.assertEquals(Double.valueOf(0), Double.valueOf(toggledCount));
		
		String[] itemIcons = {"package","class","method_public", "constant", "property", "event", "childcontrol"};
		for (String icon: itemIcons) {
			String buttonPath = "*/apiviewer.ui.SearchView/*/[@icon=" + icon + "]";
			Widget button = driver.findWidget(By.qxh(buttonPath));
			button.click();
			Thread.sleep(500);
			if (!icon.equals("event") && !icon.equals("childcontrol")) {
				Long newCount = (Long) table.getRowCount();
				Assert.assertTrue("Toggling '" + icon + "' did not change the result count! ", newCount > toggledCount);
				toggledCount = newCount;
			}
		}
	}
}
