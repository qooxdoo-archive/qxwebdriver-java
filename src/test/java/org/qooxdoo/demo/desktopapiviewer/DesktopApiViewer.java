package org.qooxdoo.demo.desktopapiviewer;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.table.Table;
import org.oneandone.qxwebdriver.ui.tree.core.AbstractItem;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.qooxdoo.demo.IntegrationTest;

public class DesktopApiViewer extends IntegrationTest {
	
	protected String tabButtonPath = "*/apiviewer.DetailFrameTabView/*/qx.ui.tabview.TabButton";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		IntegrationTest.setUpBeforeClass();
		driver.jsExecutor.executeScript("qx.locale.Manager.getInstance().setLocale('en');");
	}
	
	protected void selectView(String label) {
		String path = "*/qx.ui.toolbar.ToolBar/*/[@label=" + label + "]";
		Widget button = driver.findWidget(By.qxh(path));
		boolean selected = (Boolean) button.getPropertyValue("value");
		if (!selected) {
			button.click();
			selected = (Boolean) button.getPropertyValue("value");
			Assert.assertTrue(selected);
		}
	}

	@Test
	public void tree() {
		selectView("Content");
		
		Selectable tree = (Selectable) driver.findWidget(By.qxh("*/apiviewer.ui.PackageTree"));
		Assert.assertTrue(tree.isDisplayed());
		
		String item1Label = "bom";
		AbstractItem item1 = (AbstractItem) tree.getSelectableItem(item1Label);
		item1.click();
		Assert.assertTrue(item1.isOpen());
		
		String item2Label = "element";
		AbstractItem item2 = (AbstractItem) tree.getSelectableItem(item2Label);
		item2.click();
		Assert.assertTrue(item2.isOpen());

		String item3Label = "Dimension";
		AbstractItem item3 = (AbstractItem) tree.getSelectableItem(item3Label);
		item3.click();
		
		Widget tabButton = driver.findWidget(By.qxh(tabButtonPath));
		Assert.assertEquals("qx.bom.element.Dimension", tabButton.getPropertyValue("label"));
		
		String hash = (String) driver.executeScript("return location.hash");
		Assert.assertEquals("#qx.bom.element.Dimension", hash);
		
	}
	
	@Test
	public void search() {
		selectView("Search");
		
		String tablePath = "*/apiviewer.ui.SearchView/*/qx.ui.table.Table";
		Table table = (Table) driver.findWidget(By.qxh(tablePath));
		Long initialRowCount = (Long) table.getRowCount();
		Assert.assertEquals(Double.valueOf(0), Double.valueOf(initialRowCount));
		
		String searchFieldPath = "*/apiviewer.ui.SearchView/*/qx.ui.form.TextField";
		Widget searchField = driver.findWidget(By.qxh(searchFieldPath));
		searchField.sendKeys("window");
		Long resultRowCount = (Long) table.getRowCount();
		Assert.assertTrue(resultRowCount > 0);
		
		String namespaceFieldPath = "*/apiviewer.ui.SearchView/qx.ui.container.Composite/child[3]";
		Widget namespaceField = driver.findWidget(By.qxh(namespaceFieldPath));
		namespaceField.sendKeys("window");
		Long filteredRowCount = (Long) table.getRowCount();
		Assert.assertTrue(filteredRowCount < resultRowCount);
		
		Widget toggleButton = driver.findWidget(By.qxh("*/apiviewer.ui.SearchView/*/[@label=Toggle Filters]"));
		toggleButton.click();
		Long toggledCount = (Long) table.getRowCount();
		Assert.assertEquals(Double.valueOf(0), Double.valueOf(toggledCount));
		
		String[] itemIcons = {"package","class","method_public", "constant", "property", "event", "childcontrol"};
		for (String icon: itemIcons) {
			String buttonPath = "*/apiviewer.ui.SearchView/*/[@icon=" + icon + "]";
			Widget button = driver.findWidget(By.qxh(buttonPath));
			button.click();
			if (!icon.equals("event") && !icon.equals("childcontrol")) {
				Long newCount = (Long) table.getRowCount();
				Assert.assertTrue(newCount > toggledCount);
				toggledCount = newCount;
			}
		}
	}
	
	protected void loadFromHash(String className) {
		String url = driver.getCurrentUrl();
		String[] parts = url.split("#");
		url = parts[0] + "#" + className;
		driver.getWebDriver().get(url);
		Widget tabButton = driver.findWidget(By.qxh(tabButtonPath));
		String tabLabel = null;
		tabLabel = (String) tabButton.getPropertyValue("label");
		Assert.assertEquals(className, tabLabel);
	}
	
	protected void testProperties() {
		String propertiesPath = "*/qx.ui.toolbar.ToolBar/*/[@label=Properties]";
		Widget propertiesButton = driver.findWidget(By.qxh(propertiesPath));
		boolean propertiesActive = (Boolean) propertiesButton.getPropertyValue("value"); 
		Assert.assertTrue(propertiesActive);
		String propertyItemPath = "//div[contains(@class, 'info-panel')]/descendant::span[text()='getLiveResize']";
		WebElement propertyItem = driver.findElement(By.xpath(propertyItemPath));
		Assert.assertTrue(propertyItem.isDisplayed());
		propertiesButton.click();
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			propertyItem = driver.findElement(By.xpath(propertyItemPath));
			Assert.assertTrue("Property method was not hidden!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		propertiesButton.click();
	}
	
	protected void testClassItem(String item, String method) {
		String itemPath = "*/qx.ui.toolbar.ToolBar/*/[@label=" + item + "]";
		Widget itemButton = driver.findWidget(By.qxh(itemPath));
		boolean itemActive = (Boolean) itemButton.getPropertyValue("value"); 
		Assert.assertFalse(itemActive);
		String methodPath = "//div[contains(@class, 'info-panel')]/descendant::span[text()='" + method + "']";
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			driver.findElement(By.xpath(methodPath));
			Assert.assertTrue(item + " item " + method + " was not hidden initially!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		itemButton.click();
		
		WebElement methodItem = driver.findElement(By.xpath(methodPath));
		Assert.assertTrue(methodItem.isDisplayed());
		itemButton.click();
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			driver.findElement(By.xpath(methodPath));
			Assert.assertTrue(item + " item " + method + " was not hidden!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
	protected void testIncludes() {
		String inheritedItemPath = "//div[contains(@class, 'info-panel')]/descendant::span[text()='changeTextColor']";
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			driver.findElement(By.xpath(inheritedItemPath));
			Assert.assertTrue("Inherited method was not hidden!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		
		String includesPath = "*/qx.ui.toolbar.ToolBar/*/[@label=Includes]";
		Selectable includesButton = (Selectable) driver.findWidget(By.qxh(includesPath));
		includesButton.selectItem("Inherited");		
		WebElement inheritedItem = driver.findElement(By.xpath(inheritedItemPath));
		Assert.assertTrue(inheritedItem.isDisplayed());

		includesButton.selectItem("Inherited");
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		try {
			driver.findElement(By.xpath(inheritedItemPath));
			Assert.assertTrue("Inherited method was not hidden!", false);
		} catch(NoSuchElementException e) {}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}

	@Test
	public void classItems() {
		String className = "qx.ui.table.pane.Scroller";
		loadFromHash(className);
		
		testProperties();
		testIncludes();
		testClassItem("Protected", "_hideResizeLine");
		testClassItem("Private", "__isAtEdge");
		testClassItem("Internal", "getVerticalScrollBarWidth");
	}
	
	@Test
	public void link() {
		String className = "qx.ui.core.Widget";
		loadFromHash(className);
		
		String internalTarget = "#capture";
		WebElement internalLink= driver.findElement(By.xpath("//a[text()='" + internalTarget + "']"));
		internalLink.click();
		String hashAfter = (String) driver.executeScript("return location.hash;");
		Assert.assertEquals("#qx.ui.core.Widget~capture", hashAfter);
		
		String subClass = "qx.ui.basic.Atom";
		WebElement subClassLink = driver.findElement(By.xpath("//a[text()='" + subClass + "']"));
		subClassLink.click();
		
		Widget tabButton = driver.findWidget(By.qxh(tabButtonPath));
		Assert.assertEquals(subClass, tabButton.getPropertyValue("label"));
		hashAfter = (String) driver.executeScript("return location.hash;");
		Assert.assertEquals("#qx.ui.basic.Atom", hashAfter);
	}
	
	@Test
	public void toggleDetail() {
		String className = "qx.ui.core.Widget";
		loadFromHash(className);
		
		String detailHeadlinePath = "//div[contains(@class, 'info-panel')]/descendant::div[contains(@class, 'item-detail-headline')]";
		try {
			driver.findElement(By.xpath(detailHeadlinePath));
			Assert.assertTrue("Constructor details should be hidden initially!", false);
		} catch(NoSuchElementException e) {}
		
		WebElement constructorDetailToggle = driver.findElement(By.xpath("//div[contains(@class, 'info-panel')]/descendant::td[contains(@class, 'toggle')]/img"));
		constructorDetailToggle.click();
		WebElement detailHeadline = driver.findElement(By.xpath(detailHeadlinePath));
		Assert.assertTrue(detailHeadline.isDisplayed());
		
		constructorDetailToggle.click();
		try {
			detailHeadline = driver.findElement(By.xpath(detailHeadlinePath));
			Assert.assertTrue("Constructor details could not be hidden!", false);
		} catch(NoSuchElementException e) {}
	}
	
	@Test
	public void tabs() {
		loadFromHash("qx.ui.form.Button");
		
		String newTabClass = "qx.ui.form.MenuButton";
		WebElement link = driver.findElement(By.xpath("//a[text()='" + newTabClass + "']"));
		Actions action = new Actions(driver.getWebDriver());
		action.keyDown(Keys.SHIFT);
		action.click(link);
		action.keyUp(Keys.SHIFT);
		action.perform();
		
		String newTabPath = "*/apiviewer.DetailFrameTabView/*/[@label=" + newTabClass + "]";
		Widget newTabButton = driver.findWidget(By.qxh(newTabPath));
		Assert.assertTrue(newTabButton.isDisplayed());
		
		String closeButtonPath = newTabPath + "/qx.ui.form.Button";
		Widget closeButton = driver.findWidget(By.qxh(closeButtonPath));
		Assert.assertTrue(closeButton.isDisplayed());
		closeButton.click();
		
		try {
			driver.findWidget(By.qxh(newTabPath));
			Assert.assertTrue("New tab was not closed!", false);
		} catch(NoSuchElementException e) {}
	}
}
