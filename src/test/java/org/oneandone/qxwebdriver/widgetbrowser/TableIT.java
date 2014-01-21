package org.oneandone.qxwebdriver.widgetbrowser;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.table.Table;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class TableIT extends Common {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.setUpBeforeClass();
		selectTab("Table");
	}
	
	@Test
	public void tableHeaders() {
		Table table = (Table) tabPage.findWidget(By.qxh("*/qx.ui.table.Table"));
		List<String> headerLabels = table.getHeaderLabels();
		Assert.assertArrayEquals(new String[] { "ID", "A number", "A date",
				"Boolean" },
				headerLabels.toArray(new String[headerLabels.size()]));
	}
	
	@Test
	public void sortByColumn() {
		Table table = (Table) tabPage.findWidget(By.qxh("*/qx.ui.table.Table"));
		Widget idColumnHeader = table.getHeaderCell("ID");
		String sortIcon = (String) idColumnHeader.getPropertyValue("sortIcon");
		Assert.assertNull(sortIcon);
		idColumnHeader.click();
		sortIcon = (String) idColumnHeader.getPropertyValue("sortIcon");
		Assert.assertTrue(sortIcon.contains("ascending"));
		idColumnHeader.click();
		sortIcon = (String) idColumnHeader.getPropertyValue("sortIcon");
		Assert.assertTrue(sortIcon.contains("descending"));
	}

	@Test
	public void table() {
		Table table = (Table) tabPage.findWidget(By.qxh("*/qx.ui.table.Table"));
		//table.scrollTo("y", 5000);
		//table.getCellByText("26").click();
		
		Actions builder = new Actions(driver.getWebDriver());
		builder.keyDown(Keys.CONTROL)
			.click(table.getCellByText("26"))
			.click(table.getCellByText("32"))
			.keyUp(Keys.CONTROL)
			.perform();
		
		List<HashMap> selectedRanges = table.getSelectedRanges();
		Assert.assertEquals(2, selectedRanges.size());
		
		HashMap<String, Long> range0 = selectedRanges.get(0);
		Assert.assertEquals(26, (int) (long) range0.get("minIndex"));
		Assert.assertEquals(26, (int) (long) range0.get("maxIndex"));
		
		HashMap<String, Long> range1 = selectedRanges.get(1);
		Assert.assertEquals(32, (int) (long) range1.get("minIndex"));
		Assert.assertEquals(32, (int) (long) range1.get("maxIndex"));
	}
	
	@Test public void columnMenu() {
		Table table = (Table) tabPage.findWidget(By.qxh("*/qx.ui.table.Table"));
		Selectable colMenuButton = (Selectable) table.getColumnMenuButton();
		colMenuButton.selectItem("A number");
		
		List<String> headerLabels = table.getHeaderLabels();
		System.out.println(headerLabels);
		Assert.assertArrayEquals(new String[] { "ID", "A date", "Boolean" },
				headerLabels.toArray(new String[headerLabels.size()]));
	}
}
