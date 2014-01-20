package org.oneandone.qxwebdriver.widgetbrowser;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.table.Table;
import org.openqa.selenium.WebElement;

public class TableIT extends Common {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.setUpBeforeClass();
		selectTab("Table");
	}

	@Test
	public void table() {
		Table table = (Table) tabPage.findWidget(By.qxh("*/qx.ui.table.Table"));
		//table.scrollTo("y", 5000);

		List<String> headerLabels = table.getHeaderLabels();
		Assert.assertArrayEquals(new String[] { "ID", "A number", "A date",
				"Boolean" },
				headerLabels.toArray(new String[headerLabels.size()]));

		String cellPath = "//div[contains(@class, 'qooxdoo-table-cell') and text()='26']";
		table.scrollToChild("y", org.openqa.selenium.By.xpath(cellPath));
		WebElement cell = table.findElement(org.openqa.selenium.By.xpath(cellPath));
		cell.click();
		
		List<HashMap> selectedRanges = table.getSelectedRanges();
		Assert.assertEquals(1, selectedRanges.size());
		HashMap<String, Long> range = selectedRanges.get(0);
		Assert.assertEquals(26, (int) (long) range.get("minIndex"));
		Assert.assertEquals(26, (int) (long) range.get("maxIndex"));
	}
}
