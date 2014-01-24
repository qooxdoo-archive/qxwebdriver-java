package org.qooxdoo.demo.demobrowser.table;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.form.IBooleanForm;
import org.oneandone.qxwebdriver.ui.table.Table;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class TableCellEditor {
	static QxWebDriver driver;
	static final String AUT_URL = "http://demo.qooxdoo.org/current/demobrowser/demo/table/Table_Cell_Editor.html";

	public Table table;
	
	@Before
	public void setUp() {
		table = (Table) driver.findWidget(By.qxh("*/qx.ui.table.Table"));
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FirefoxDriver webDriver = new FirefoxDriver();
		// ChromeDriver webDriver = new ChromeDriver();
		// DesiredCapabilities capabilities =
		// DesiredCapabilities.internetExplorer();
		// RemoteWebDriver webDriver = new RemoteWebDriver(new
		// URL("http://172.17.14.65:4440/wd/hub"), capabilities);
		driver = new QxWebDriver(webDriver);
		driver.manage().window().maximize();
		driver.get(AUT_URL);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
	}
	
	@Test
	public void textField() {
		String cellXpath = "div[contains(@class, 'qooxdoo-table-cell') and position() = 2]";
		String newText = "hsimpson";
		
		WebElement row = table.scrollToRow(0);
		WebElement userNameCell = row.findElement(By.xpath(cellXpath));
		Actions builder = new Actions(driver.getWebDriver());
		builder.doubleClick(userNameCell).perform();

		WebElement editor = table.getCellEditor();
		editor.sendKeys(Keys.BACK_SPACE);
		editor.sendKeys(Keys.BACK_SPACE);
		editor.sendKeys(Keys.BACK_SPACE);
		editor.sendKeys(Keys.BACK_SPACE);
		editor.sendKeys(newText);
		editor.sendKeys(Keys.RETURN);
		
		row = table.scrollToRow(0);
		userNameCell = row.findElement(By.xpath(cellXpath));
		Assert.assertEquals(newText,  userNameCell.getText());
	}
	
	@Test
	public void comboBox() {
		String cellXpath = "div[contains(@class, 'qooxdoo-table-cell') and position() = 2]";
		String newText = "admin";
		
		WebElement row = table.scrollToRow(2);
		WebElement roleCell = row.findElement(By.xpath(cellXpath));
		Actions builder = new Actions(driver.getWebDriver());
		builder.doubleClick(roleCell).perform();

		Selectable editor = (Selectable) table.getCellEditor();
		editor.selectItem("admin");
		editor.sendKeys(Keys.RETURN);
		
		row = table.scrollToRow(2);
		roleCell = row.findElement(By.xpath(cellXpath));
		Assert.assertEquals(newText,  roleCell.getText());
		
		newText = "safety inspector";
		builder.doubleClick(roleCell).perform();
		editor = (Selectable) table.getCellEditor();
		editor.sendKeys(newText);
		editor.sendKeys(Keys.RETURN);
		row = table.scrollToRow(2);
		roleCell = row.findElement(By.xpath(cellXpath));
		Assert.assertEquals(newText,  roleCell.getText());
	}
	
	@Test
	public void checkBox() {
		String cellXpath = "div[contains(@class, 'qooxdoo-table-cell') and position() = 2]";
		
		WebElement row = table.scrollToRow(7);
		WebElement newsletterCell = row.findElement(By.xpath(cellXpath));
		Actions builder = new Actions(driver.getWebDriver());
		builder.doubleClick(newsletterCell).perform();

		IBooleanForm editor = (IBooleanForm) table.getCellEditor();
		Assert.assertTrue(editor.isSelected());
		editor.click();
		Assert.assertFalse(editor.isSelected());
		table.click();
		
	}
}
