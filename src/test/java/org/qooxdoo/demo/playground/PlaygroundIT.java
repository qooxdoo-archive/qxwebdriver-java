package org.qooxdoo.demo.playground;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.qooxdoo.demo.IntegrationTest;


public class PlaygroundIT extends IntegrationTest{
	
	public static String qxVersion = null;
	
	public static String initialHandle = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		IntegrationTest.setUpBeforeClass();
		initialHandle = driver.getWindowHandle();
		qxVersion = (String) driver.executeScript("return qx.core.Environment.get('qx.version')");
	}

	/**
	 * Check if syntax highlighting is displayed before tests starting
	 *	and turned it on
	 */
	@Before
	public void setUpBeforeTest() throws InterruptedException{
		Thread.sleep(1000);
		Widget hightlightButton = driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Syntax Highlighting]"));
		Boolean displayed =(Boolean)hightlightButton.getPropertyValue("value");	
		if(!displayed){
			driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Syntax Highlighting]")).click();
		}
	}
	
	public void checkLink(String expectedUrl) throws InterruptedException {
		checkLink(expectedUrl, true);
	}
	
	public void checkLink(String expectedUrl, Boolean exact) throws InterruptedException {
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> itr = handles.iterator();
		while (itr.hasNext()) {
			String handle = itr.next();
			if (!handle.equals(initialHandle)) {
				driver.switchTo().window(handle);
				Thread.sleep(1000);
				String newUrl = driver.getCurrentUrl();
				driver.close();
				driver.switchTo().window(initialHandle);
				if (exact) {
					Assert.assertEquals(expectedUrl, newUrl);
				} else {
					Assert.assertTrue(newUrl.startsWith(expectedUrl));
				}
			}
		}
	}

	/**
	 * test to load all samples, 
	 * it is correct, if the right play area exists
	 * and ace content text is not the same as before clicking a sample
	 */
	@Test
	public void loadSamples () throws InterruptedException{
		//contains sample name and play area type
		Map<String, String> container  = new HashMap<String, String>();
		container.put( "Hello World", "qx.ui.form.Button");
		container.put( "Window", "qx.ui.window.Window");
		container.put( "Dialog", "qx.ui.tabview.TabView");
		container.put( "Calculator", "qx.ui.window.Window");
		container.put( "Table", "qx.ui.window.Window");
		container.put( "Tree", "qx.ui.tree.Tree");
		container.put( "Data Binding", "qx.ui.form.TextField");
		container.put( "YQL Binding", "qx.ui.form.List");
		
		By widgetCellLocator =By.qxh("*/qx.ui.list.List/*/qx.ui.virtual.layer.WidgetCell");
		Widget widgetCell = driver.findWidget(widgetCellLocator);
		List<Widget> children = widgetCell.getChildren();
		Iterator<Widget> iter = children.iterator();
		WebElement aceContent = driver.findElement(By.xpath("//div[contains(@class, 'ace_content')]"));
		String contentText = null;
		
		// skip over 'Static'
		iter.next();
		String label = null;
		while (iter.hasNext()) {
			Widget item = iter.next();
			label = item.getText();
			//skip over 'User', if there are user scripts saved
			if(!label.equals("User")){
			item.click();
			Widget playArea= driver.findWidget(By.qxh("*/qx.ui.root.Inline"));
			List<Widget> playAreaType = playArea.getChildren();
			String type = playAreaType.get(0).toString();
			// check if the type of play area is the same as in the map above
			assertTrue(type.contains(container.get(label)));		
			String newText= aceContent.getText();
			//check if the next sample has been clicked
			Assert.assertNotEquals(newText, contentText);
			contentText= newText;
			} else{
				break;
			}
		}
	}

	/**
	 * checks if the toggle button 'Syntax Highlighting' works correctly
	 */
	@Test
	public void toggleSyntaxHighlighting() throws InterruptedException {
		By widgetCellLocator =By.qxh("*/qx.ui.list.List/*/qx.ui.virtual.layer.WidgetCell");
		Widget widgetCell = driver.findWidget(widgetCellLocator);
		List<Widget> children = widgetCell.getChildren();
		Iterator<Widget> iter = children.iterator();
		// skip over 'Static'
		iter.next();
		
		while (iter.hasNext()) {
			Widget item = iter.next();
			item.click();
			Thread.sleep(900);
			// Syntax should be highlighted
			//before the toggle button has been clicked at the first time
			WebElement aceContent = driver.findElement(By.xpath("//div[contains(@class, 'ace_layer ace_gutter-layer ace_folding-enabled')]"));
			Boolean isHighlighted= aceContent.isDisplayed();
			assertTrue(isHighlighted);
			//after clicking toggle button, syntax highlighting should be turned off
			driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Syntax Highlighting]")).click();
			aceContent = driver.findElement(By.xpath("//div[contains(@class, 'ace_layer ace_gutter-layer ace_folding-enabled')]"));
			isHighlighted = aceContent.isDisplayed();
			assertFalse(isHighlighted);
			driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Syntax Highlighting]")).click();
		}
	}

	/*
	 * test to click 'Log' button, clear log content 
	 * and check if the content has been cleared
	 */
	@Test
	public void checkLogClearButton(){
		
		driver.findWidget(By.qxh("*/playground.view.Toolbar/*/[@label=Log]")).click();
		//after clicking 'Log' button content should not be empty 
		WebElement LogContent = driver.findElement(By.xpath("//div[contains(@class, 'qxappender')]"));
		assertTrue(!LogContent.getText().equals(""));
		//clearing log content
		driver.findWidget(By.qxh("*/qx.ui.splitpane.Pane/*/[@label=Clear]")).click();
		LogContent = driver.findElement(By.xpath("//div[contains(@class, 'qxappender')]"));
		//check if log content has been cleared
		assertTrue(LogContent.getText().equals(""));
	}

	/**
	 * test to run code which has been changed without saving it
	 * 
	 */
	@Test
	public void runningChangedCode() throws InterruptedException{
		// switch to text area (without syntax highlighting) to edit the code
		driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Syntax Highlighting]")).click();
		Widget editor = driver.findWidget(By.qxh("*/playground.view.Editor"));
		WebElement textarea= editor.findElement(By.xpath("//textarea[contains(@class, 'qx-abstract-field qx-placeholder-color')]"));
		//clear code
		textarea.clear();
		//'Hello World' sample, which creates a button with label 'First Button'
		// will be changed to 'Second Button'
		textarea.sendKeys("// Create a button\n"
				+ "var button1 = new qx.ui.form.Button(\"Second Button\", \"icon/22/apps/internet-web-browser.png\"\n);"
				+ "// Document is the application root\n"
				+ "var doc = this.getRoot();\n"
				+ "// Add button to document at fixed coordinates\n"
				+ "doc.add(button1,\n"
				+ "{\n"
				+ " left : 100,\n"
				+ " top  : 50\n"
				+ "});\n"
				+ "// Add an event listener\n"
				+ "button1.addListener(\"execute\", function(e) {\n"
				+ "alert(\"Hello World!\");\n"
				+ "});\n");
		driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Run]")).click();
		//check if a button with new label has been found after running 
		Widget playArea= driver.waitForWidget(By.qxh("*/qx.ui.root.Inline/[@label=Second Button]"), 10);
		assertTrue(playArea.isDisplayed());
	}

	/**
	 * test to check if saving an example works correctly
	 * the code has not been modified before
	 */
	@Test
	public void saveExample(){
		By locator = By.qxh("*/[@source=document-save.png]");
		Widget saveButton = driver.findWidget(locator);
		saveButton.click();
		Alert savePrompt = driver.switchTo().alert();
		savePrompt.sendKeys("Saved Sample");
		savePrompt.accept();
	}

	/**
	 * test to check 'Saving As' button with modified code 
	 */
	@Test
	public void saveExampleAs(){
		driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Syntax Highlighting]")).click();
		Widget editor = driver.findWidget(By.qxh("*/playground.view.Editor"));
		WebElement textarea= editor.findElement(By.xpath("//textarea[contains(@class, 'qx-abstract-field qx-placeholder-color')]"));
		textarea.clear();
		textarea.sendKeys("// Create a button\n"
				+ "var button1 = new qx.ui.form.Button(\"Second Button\", \"icon/22/apps/internet-web-browser.png\"\n);"
				+ "// Document is the application root\n"
				+ "var doc = this.getRoot();\n"
				+ "// Add button to document at fixed coordinates\n"
				+ "doc.add(button1,\n"
				+ "{\n"
				+ " left : 100,\n"
				+ " top  : 50\n"
				+ "});\n"
				+ "// Add an event listener\n"
				+ "button1.addListener(\"execute\", function(e) {\n"
				+ "alert(\"Hello World!\");\n"
				+ "});\n");
		By locator = By.qxh("*/[@source=document-save-as.png]");
		Widget saveButton = driver.findWidget(locator);
		saveButton.click();
		Alert savePrompt = driver.switchTo().alert();
		savePrompt.sendKeys("Saved(As) Sample");
		savePrompt.accept();
	}

	/**
	 * test to delete a saved user sample 
	 */
	@Test
	public void deleteUserScript() throws InterruptedException{
		
		By locator = By.qxh("*/[@source=document-save.png]");
		Widget saveButton = driver.findWidget(locator);
		saveButton.click();
		Alert savePrompt = driver.switchTo().alert();
		savePrompt.sendKeys("test example 2");
		savePrompt.accept();
		WebElement widgetCell =driver.findElement(By.qxh("*/qx.ui.list.List/*/qx.ui.virtual.layer.WidgetCell/[@label=test example 2]"));
		widgetCell.click();
		By locatorDelete = By.qxh("*/[@source=user-trash.png]");
		Widget deleteButton = driver.findWidget(locatorDelete);
		deleteButton.click();
		Thread.sleep(1000);
		WebElement deletedSample =driver.findElement(By.qxh("*/qx.ui.list.List/*/qx.ui.virtual.layer.WidgetCell/[@label=test example 2]"));
		assertTrue(deletedSample==null);

	}

	/**
	 * test to rename a saved user sample 
	 */
	@Test
	public void renameSample(){
		
		By locator = By.qxh("*/[@source=document-save.png]");
		Widget saveButton = driver.findWidget(locator);
		saveButton.click();
		Alert savePrompt = driver.switchTo().alert();
		savePrompt.sendKeys("test example 1");
		savePrompt.accept();
		WebElement widgetCell =driver.findElement(By.qxh("*/qx.ui.list.List/*/qx.ui.virtual.layer.WidgetCell/[@label=test example 1]"));
		widgetCell.click();
		By locatorRename = By.qxh("*/[@source=format-text-direction-ltr.png]");
		Widget renameButton = driver.findWidget(locatorRename);
		renameButton.click();
		Alert renamePrompt = driver.switchTo().alert();
		renamePrompt.sendKeys("Renamed Sample");
		renamePrompt.accept();
		WebElement renamedSample =driver.findElement(By.qxh("*/qx.ui.list.List/*/qx.ui.virtual.layer.WidgetCell/[@label=Renamed Sample]"));
		assertTrue(renamedSample!=null);
	}

	/**
	 * test to reload website after user script has been saved
	 * the saved sample should be found after reload
	 * @throws InterruptedException 
	 */
	@Test
	public void userSamplesReload() throws InterruptedException{
		By locator = By.qxh("*/[@source=document-save.png]");
		Widget saveButton = driver.findWidget(locator);
		saveButton.click();
		Alert savePrompt = driver.switchTo().alert();
		savePrompt.sendKeys("reload example");
		savePrompt.accept();
		//reload
		Thread.sleep(2000);
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
		WebElement reloadedSample =driver.findElement(By.qxh("*/qx.ui.list.List/*/qx.ui.virtual.layer.WidgetCell/[@label=reload example]"));
		assertTrue(reloadedSample != null);
	}

	/**
	 * test to check if an alert is displayed after discarding a modified code
	 * by switching to another sample
	 */
	@Test
	public void discardCode(){
		driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Syntax Highlighting]")).click();
		Widget editor = driver.findWidget(By.qxh("*/playground.view.Editor"));
		WebElement textarea= editor.findElement(By.xpath("//textarea[contains(@class, 'qx-abstract-field qx-placeholder-color')]"));
		textarea.clear();
		textarea.sendKeys("// Create a button\n"
				+ "var button1 = new qx.ui.form.Button(\"Third Button\", \"icon/22/apps/internet-web-browser.png\"\n);"
				+ "// Document is the application root\n"
				+ "var doc = this.getRoot();\n"
				+ "// Add button to document at fixed coordinates\n"
				+ "doc.add(button1,\n"
				+ "{\n"
				+ " left : 100,\n"
				+ " top  : 50\n"
				+ "});\n"
				+ "// Add an event listener\n"
				+ "button1.addListener(\"execute\", function(e) {\n"
				+ "alert(\"Hello World!\");\n"
				+ "});\n");
		driver.findElement(By.qxh("*/qx.ui.list.List/*/qx.ui.virtual.layer.WidgetCell/[@label=Window]")).click();
		Alert discardPrompt = driver.switchTo().alert();
		discardPrompt.accept();
	}

	/**
	 * test to check URL after clicking 'API Viewer' button 
	 * @throws InterruptedException 
	 */
	@Test
	public void apiViewerLink() throws InterruptedException{
		driver.findElement(By.qxh("*/playground.view.Toolbar/[@label=API Viewer]")).click();
		Thread.sleep(1000);
		checkLink("http://demo.qooxdoo.org/" + qxVersion + "/apiviewer/#qx", false);
	}

	/**
	 * test to check URL after clicking 'Manual' button
	 */
	@Test
	public void manualLink() throws InterruptedException{
		driver.findElement(By.qxh("*/playground.view.Toolbar/[@label=Manual]")).click();
		Thread.sleep(1000);
		checkLink("http://manual.qooxdoo.org/" + qxVersion + "/");
	}

	/**
	 * test to check URL after clicking 'Demo Browser' button
	 */
	@Test
	public void demoBrowserLink() throws InterruptedException{
		driver.findElement(By.qxh("*/playground.view.Toolbar/[@label=Demo Browser]")).click();
		Thread.sleep(1000);
		checkLink("http://demo.qooxdoo.org/" + qxVersion + "/demobrowser/#");
	}

	/**
	 * test to check URL after clicking 'Shorten URL' button
	 */
	@Test
	public void shortenURLLink() throws InterruptedException{
		driver.findElement(By.qxh("*/playground.view.Toolbar/[@label=Shorten URL]")).click();
		Thread.sleep(1000);
		checkLink("http://tinyurl.com/create.php?url=", false);
	}

	/**
	 * test to check URL after clicking the 'CodePen' link in the 'Website'tab.
	 */
	@Test
	public void openCodePenLink() throws InterruptedException{
		driver.findElement(By.qxh("*/playground.view.Header/[@label=Website]")).click();
		Thread.sleep(2000);
		//there are two inputs, the first is hidden
		List<WebElement> inputs = driver.findElements(By.xpath("//input[@value='CodePen']"));
		inputs.get(1).click();
		checkLink("http://codepen.io/pen");
	}

	/**
	 * test to check URL after modified code is running
	 */
	@Test
	public void checkChangedCodeURL() throws InterruptedException{
		driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Syntax Highlighting]")).click();
		Widget editor = driver.findWidget(By.qxh("*/playground.view.Editor"));
		WebElement textarea= editor.findElement(By.xpath("//textarea[contains(@class, 'qx-abstract-field qx-placeholder-color')]"));
		textarea.clear();
		textarea.sendKeys("// Create a button\n"
				+ "var button1 = new qx.ui.form.Button(\"Second Button\", \"icon/22/apps/internet-web-browser.png\"\n);"
				+ "// Document is the application root\n"
				+ "var doc = this.getRoot();\n"
				+ "// Add button to document at fixed coordinates\n"
				+ "doc.add(button1,\n"
				+ "{\n"
				+ " left : 100,\n"
				+ " top  : 50\n"
				+ "});\n"
				+ "// Add an event listener\n"
				+ "button1.addListener(\"execute\", function(e) {\n"
				+ "alert(\"Hello World!\");\n"
				+ "});\n");
		driver.findWidget(By.qxh("*/qx.ui.container.Composite/*/[@label=Run]")).click();
		String currentURL = driver.getCurrentUrl();
		assertTrue(currentURL.contains("Second"));
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
		driver.get(currentURL);
		Widget playArea= driver.waitForWidget(By.qxh("*/qx.ui.root.Inline/[@label=Second Button]"), 10);
		assertTrue(playArea.isDisplayed());
	}

	// reload after every test
	@After
	public void tearDownAfterTest() throws Exception{
		driver.switchTo().window(initialHandle);
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

}
