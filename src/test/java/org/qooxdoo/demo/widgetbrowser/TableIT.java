package org.qooxdoo.demo.widgetbrowser;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.table.Table;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TableIT extends WidgetBrowser {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WidgetBrowser.setUpBeforeClass();
		selectTab("Table");
	}
	
public Table table;
	
	@Before
	public void setUp() {
		table = (Table) tabPage.findWidget(By.qxh("*/qx.ui.table.Table"));
	}
	
	protected boolean isIe() {
		String browser = System.getProperty("org.qooxdoo.demo.browsername");
		if (browser.contains("explorer")) {
			return true;
		}
		
		return false;
	}
	
	protected boolean isFirefox() {
		if (System.getProperty("org.qooxdoo.demo.platform").equalsIgnoreCase("windows") &&
			System.getProperty("org.qooxdoo.demo.browsername").equalsIgnoreCase("firefox") &&
			System.getProperty("org.qooxdoo.demo.browserversion").equalsIgnoreCase("stable")) {
			return true;
		}
		return false;
	}

	@Test
	public void scrollToRow() {
		if (isIe()) {
			return;
		}
		// select rows by index
		WebElement row = table.scrollToRow(23);		
		WebElement firstCell = row.findElement(By.xpath("div[contains(@class, 'qooxdoo-table-cell')]"));
		Assert.assertEquals("23", firstCell.getText());
		
		row = table.scrollToRow(3);
		firstCell = row.findElement(By.xpath("div[contains(@class, 'qooxdoo-table-cell')]"));
		Assert.assertEquals("3", firstCell.getText());
	}

	@Test
	public void getCellByText() {
		// ctrl-click doesn't work in FF stable/Win
		if (isIe() || isFirefox()) {
			return;
		}
		// ctrl-click two rows and verify the selection ranges
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
	
	@Test
	public void editCell() throws InterruptedException {
		String browserName = System.getProperty("org.qooxdoo.demo.browsername");
		String browserVersion = System.getProperty("org.qooxdoo.demo.browserversion");
		boolean condition = browserName.contains("internet") && browserVersion.equals("8");
		org.junit.Assume.assumeTrue(!condition);
		String cellXpath = "div[contains(@class, 'qooxdoo-table-cell') and position() = 3]";
		String newText = "Hello WebDriver!";
		
		// Scroll to row #12 and select cell #3
		WebElement row = table.scrollToRow(12);
		WebElement dateCell = row.findElement(By.xpath(cellXpath));
		dateCell.click();
		
		// Double click cell #3 to activate edit mode
		Actions builder = new Actions(driver.getWebDriver());
		builder.doubleClick(dateCell).perform();

		Widget editor = table.getCellEditor();
		String old = (String) editor.getPropertyValue("value");
		
		// Clear old content
		Actions keyBuilder = new Actions(driver.getWebDriver())
			.sendKeys(Keys.END);
		for (int i = 0; i < old.length(); i++) {
			keyBuilder.sendKeys(Keys.BACK_SPACE);
		}
		keyBuilder.perform();
		
		// Type new cell content
		editor.sendKeys(newText);
		editor.sendKeys(Keys.RETURN);
		
		// update the cell element and check the new content
		row = table.scrollToRow(12);
		dateCell = row.findElement(By.xpath(cellXpath));
		Assert.assertEquals(newText,  dateCell.getText());
	}

	@Test
	public void columnMenu() {
		// use the column menu to hide a column
		List<String> headerLabels = table.getHeaderLabels();
		Assert.assertArrayEquals(new String[] { "ID", "A number", "A date", "Boolean" },
				headerLabels.toArray(new String[headerLabels.size()]));
		
		Selectable colMenuButton = (Selectable) table.getColumnMenuButton();
		colMenuButton.selectItem("A number");
		
		headerLabels = table.getHeaderLabels();
		Assert.assertArrayEquals(new String[] { "ID", "A date", "Boolean" },
				headerLabels.toArray(new String[headerLabels.size()]));
		colMenuButton.selectItem("A number");
	}

	@Test
	public void sortByColumn() {
		if (isIe()) {
			return;
		}
		// click column headers to set the table's sorting order
		Widget idColumnHeader = table.getHeaderCell("ID");
		String sortIcon = (String) idColumnHeader.getPropertyValue("sortIcon");
		Assert.assertNull(sortIcon);
		idColumnHeader.click();
		sortIcon = (String) idColumnHeader.getPropertyValue("sortIcon");
		Assert.assertTrue(sortIcon.contains("ascending"));
		idColumnHeader.click();
		sortIcon = (String) idColumnHeader.getPropertyValue("sortIcon");
		Assert.assertTrue(sortIcon.contains("descending"));
		// back to default sorting
		idColumnHeader.click();
	}

}
