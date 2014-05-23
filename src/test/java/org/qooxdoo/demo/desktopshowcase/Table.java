package org.qooxdoo.demo.desktopshowcase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Table extends Desktopshowcase {

	public By tableLocator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/qx.ui.window.Desktop/qx.ui.window.Window/qx.ui.table.Table");
	public org.oneandone.qxwebdriver.ui.table.Table table = null;

	@Before
	public void setUp() throws Exception {
		selectPage("Table");
		table = getTable();
	}

	public org.oneandone.qxwebdriver.ui.table.Table getTable() {
		WebElement tableEl = getRoot().findElement(tableLocator);
		org.oneandone.qxwebdriver.ui.table.Table table = (org.oneandone.qxwebdriver.ui.table.Table) driver.getWidgetForElement(tableEl);
		return table;
	}

	public ExpectedCondition<Boolean> tableDataLoaded() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return table.getRowCount() > 1;
			}

			@Override
			public String toString() {
				return "Showcase page has finished loading.";
			}
		};
	}
	
	public void waitUntilTableDataLoaded() {
		new WebDriverWait(driver, 20, 250).until(tableDataLoaded());
	}

	@Test
	public void table() throws InterruptedException {
		Assert.assertTrue(table.isDisplayed());
		waitUntilTableDataLoaded();
	}
}
