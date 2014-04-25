package org.qooxdoo.demo.desktopapiviewer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.table.Table;
import org.openqa.selenium.WebElement;
import org.qooxdoo.demo.IntegrationTest;

public abstract class DesktopApiViewer extends IntegrationTest {
	
	protected String tabButtonPath = "*/apiviewer.DetailFrameTabView/*/qx.ui.tabview.TabButton";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		IntegrationTest.setUpBeforeClass();
		driver.jsExecutor.executeScript("qx.locale.Manager.getInstance().setLocale('en');");
	}
	
	protected static void selectView(String label) {
		String path = "*/qx.ui.toolbar.ToolBar/*/[@label=" + label + "]";
		Widget button = driver.findWidget(By.qxh(path));
		boolean selected = (Boolean) button.getPropertyValue("value");
		if (!selected) {
			button.click();
			selected = (Boolean) button.getPropertyValue("value");
			Assert.assertTrue(selected);
		}
	}
	
	protected static void typeInSearch(String query) {
		String searchFieldPath = "*/apiviewer.ui.SearchView/*/qx.ui.form.TextField";
		Widget searchField = driver.findWidget(By.qxh(searchFieldPath));
		searchField.sendKeys(query);
	}
	
	protected static void selectClass(String className) {
		selectView("Search");
		typeInSearch(className);
		String tablePath = "*/apiviewer.ui.SearchView/*/qx.ui.table.Table";
		Table table = (Table) driver.findWidget(By.qxh(tablePath));
		WebElement row = table.scrollToRow(0);
		row.click();
	}
}
