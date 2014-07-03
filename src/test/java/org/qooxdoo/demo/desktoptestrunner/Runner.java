package org.qooxdoo.demo.desktoptestrunner;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;
import org.qooxdoo.demo.IntegrationTest;

public class Runner extends IntegrationTest {

	@Test
	public void testAppLoads() {
		Widget treeItem= driver.findWidget(By.qxh("*/qx.ui.tree.VirtualTreeItem"));
		assertTrue(treeItem.getText().equals("qx"));
	}
	
	@Test
	public void selectTests() throws InterruptedException{
		//select test : qx -> test -> bom -> client -> Css -> 'testBorderImageSyntax'
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=bom]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=client]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=Css]")).click();
		Widget test = driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=testBorderImageSyntax]"));
		assertTrue(test.isDisplayed());
		
		//select test: qx -> test -> media -> Audio -> 'testVolume' 
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=media]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=Audio]")).click();
		Thread.sleep(750);
		Widget test2 = driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=testVolume]"));
		test2.click();
		
		assertTrue(test2.isDisplayed());

		//select test: qx -> test -> Basic ->  'testElementAttributes' 
		org.oneandone.qxwebdriver.ui.Scrollable scroll=(Scrollable) driver.findWidget(By.qxh("*/qx.ui.tree.VirtualTree"));
		Widget basic = scroll.scrollToChild("y", By.xpath("//div[text()='Basic']"));
		Thread.sleep(750);
		basic.click();
		Thread.sleep(750);
		Widget testElementAttributes = scroll.scrollToChild("y", By.xpath("//div[text() = 'testElementAttributes']"));
		Thread.sleep(750);
		testElementAttributes.click();
		assertTrue(testElementAttributes.isDisplayed());
		
	}
	
	@Test
	public void runTests() throws InterruptedException{
		
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=bom]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=client]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=Css]")).click();
		//click 'Run Tests!' button 
		WebElement run= driver.findElement(By.xpath("//div[contains(@class, 'qx-button-box-left')]"));;
		run.click();
		WebElement results= driver.findElement(By.xpath("//ul[contains(@class, 'resultPane')]"));
		assertTrue(results.getText().equals("qx.test.bom.client.Css:testBorderImageSyntax"));
	
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=application]")).click();
		Thread.sleep(1000);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=Routing]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=testAny]")).click();
		run.click();
		WebElement results2= driver.findElement(By.xpath("//ul[contains(@class, 'resultPane')]"));
		assertTrue(results2.getText().equals("qx.test.application.Routing:testAny"));
		
		Thread.sleep(750);
		org.oneandone.qxwebdriver.ui.Scrollable scroll=(Scrollable) driver.findWidget(By.qxh("*/qx.ui.tree.VirtualTree"));
		scroll.scrollToChild("y", By.xpath("//div[text() = 'Basic']"));
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=Basic]")).click();
		run.click();
		WebElement results3= driver.findElement(By.xpath("//ul[contains(@class, 'resultPane')]"));
		assertTrue(results3.getText().equals("qx.test.bom.Basic:testElementAttributes"));
	}
	
	@Test
	public void settingLogLevelWorks() throws InterruptedException{
		//  check debug mode after initializing
		By locator = By.qxh("*/[@source=system.png]");
		Widget logLevelButton = driver.findWidget(locator);
		assertTrue(logLevelButton.isDisplayed());
		Widget logContent = driver.findWidget(By.qxh("*/qx.ui.embed.Html"));
		//check if log content is empty
		assertTrue(logContent.getText().equals(""));
		//run a test
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=bom]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=client]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=Device]")).click();
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=testDetectDeviceType]")).click();
		//click 'Run Tests!' button 
		WebElement run= driver.findElement(By.xpath("//div[contains(@class, 'qx-button-box-left')]"));;
		run.click();
		logContent = driver.findWidget(By.qxh("*/qx.ui.embed.Html"));
		//log content should not be empty after running a test
		assertTrue(!logContent.getText().equals(""));
		
		//switch to log level 'Warning'
		logLevelButton.click();
		By locatorWarning = By.qxh("*/[@source=dialog-warning.png]");
		Widget logLevelButtonWarning = driver.findWidget(locatorWarning);
		logLevelButtonWarning.click();
		run.click();
		Thread.sleep(750);
		//after running a test, content should be empty
		assertTrue(logContent.getText().equals(""));
	}
	
	@Test
	public void reload() throws InterruptedException{
		Widget logContent = driver.findWidget(By.qxh("*/qx.ui.embed.Html"));
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=bom]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=client]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=Device]")).click();
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=testDetectDeviceType]")).click();
		//click 'Run Tests!' button 
		WebElement run= driver.findElement(By.xpath("//div[contains(@class, 'qx-button-box-left')]"));
		run.click();
		logContent = driver.findWidget(By.qxh("*/qx.ui.embed.Html"));
		//log content should not be empty after running a test
		assertTrue(!logContent.getText().equals(""));
		
		Widget reload = driver.findWidget(By.qxh("*/qx.ui.toolbar.PartContainer/*/[@label=Reload]"));
		reload.click();
		Thread.sleep(1500);
		//log content should be empty after reloading
		assertTrue(logContent.getText().equals(""));
		
		//result pane should be empty after reloading
		WebElement results= driver.findElement(By.xpath("//ul[contains(@class, 'resultPane')]"));
		assertTrue(results.getText().equals(""));
	}
	
	@Test
	public void autoReload() throws InterruptedException{
		Widget logContent = driver.findWidget(By.qxh("*/qx.ui.embed.Html"));
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=bom]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=client]")).click();
		Thread.sleep(750);
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=Device]")).click();
		driver.findWidget(By.qxh("*/qx.ui.virtual.layer.WidgetCell/[@label=testDetectDeviceType]")).click();
		//click 'Run Tests!' button 
		WebElement run= driver.findElement(By.xpath("//div[contains(@class, 'qx-button-box-left')]"));
		run.click();
		logContent = driver.findWidget(By.qxh("*/qx.ui.embed.Html"));
		//log content should not be empty after running a test
		assertTrue(!logContent.getText().equals(""));
		
		Widget autoReload = driver.findWidget(By.qxh("*/qx.ui.toolbar.ToolBar/*/[@label=Auto Reload]"));
		autoReload.click();
		
		run.click();
		Thread.sleep(1000);
		//result pane should be empty after reloading
		WebElement results= driver.findElement(By.xpath("//ul[contains(@class, 'resultPane')]"));
		assertTrue(!results.getText().equals(""));
		autoReload.click();
	}

	
	@After
	public void setUpAfterTest() throws Exception{
			driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
			driver.manage().window().maximize();
			driver.registerLogAppender();
			driver.registerGlobalErrorHandler();
	}

}
