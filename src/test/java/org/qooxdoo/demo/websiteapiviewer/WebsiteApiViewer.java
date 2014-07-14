package org.qooxdoo.demo.websiteapiviewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.qooxdoo.demo.Configuration;
import org.qooxdoo.demo.IntegrationTest;

public class WebsiteApiViewer extends IntegrationTest {
	
	public static WebDriver webDriver;
	
	public void scrollNav(Integer value) {
		JavascriptExecutor exec = (JavascriptExecutor) webDriver;
		exec.executeScript("arguments[0].scrollTop = " + value + ";", webDriver.findElement(By.id("navContainer")));
	}
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		webDriver = Configuration.getWebDriver();
		webDriver.manage().window().maximize();
		webDriver.get(System.getProperty("org.qooxdoo.demo.auturl"));
		Thread.sleep(4000);
	}
	
	@Before
	public void waitForList() {
		By lastItem = By.xpath("//li[@id='list-group-Plugin_API' and contains(@class, 'qx-tabs-page-closed')]");
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		webDriver.findElement(lastItem);
		webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
	@Test
	public void warning() {
		WebElement warning = webDriver.findElement(By.id("warning"));
		if (warning != null && warning.isDisplayed()) {
			Assert.assertTrue("Found warning: " + warning.getText(), false);
		}
	}
	
	@Test
	public void syntaxHighlighting() throws InterruptedException {
		webDriver.get(System.getProperty("org.qooxdoo.demo.auturl") + "#.removeAttribute");
		Thread.sleep(4000);
		WebElement sampleBlock = webDriver.findElement(By.xpath("//div[@id='.removeAttribute']/div[@class='sample']"));
		Assert.assertNotNull(sampleBlock);
		Assert.assertTrue(sampleBlock.isDisplayed());
		WebElement sampleJs = sampleBlock.findElement(By.xpath("//pre[@class='javascript']"));
		Assert.assertNotNull(sampleJs);
		Assert.assertTrue(sampleJs.isDisplayed());
		WebElement sampleSpan = sampleJs.findElement(By.xpath("//span"));
		Assert.assertNotNull(sampleSpan);
		Assert.assertTrue(sampleSpan.isDisplayed());
	}
	
	public List<String> getCategories() {
		List<WebElement> accordionButtons = webDriver.findElements(By.xpath("//div[@id='list']/ul/li[contains(@class, 'qx-tabs-button')]"));
		Assert.assertNotEquals(0, accordionButtons.size());
		List<String> categories = new ArrayList<String>();
		Iterator<WebElement> itr = accordionButtons.iterator();
		while (itr.hasNext()) {
			categories.add(itr.next().getText());
		}
		return categories;
	}
	
	@Test
	public void listNavigation() throws InterruptedException {
		List<String> categories = getCategories();
		Assert.assertTrue(categories.size() > 0);
		// Choose a random category from the nav list
		Random rnd = new Random();
		Integer catIndex = rnd.nextInt(categories.size() - 1);
		String categoryName = categories.get(catIndex);
		String catPath = "//div[@id='list']/ul/li[text()='" + categoryName + "']";
		WebElement catHeader = webDriver.findElement(By.xpath(catPath));
		String catItemPath = catPath + "/following-sibling::li";
		WebElement catItem = webDriver.findElement(By.xpath(catItemPath));
		// The category's corresponding item should be closed initially
		Assert.assertEquals(0, catItem.getSize().getHeight());
		catHeader.click();
		Thread.sleep(1000);
		Assert.assertTrue(catItem.getSize().getHeight() > 0);
		
		// Get the category's entries (methods)
		String catEntriesPath = catItemPath + "/descendant::li[starts-with(@class, 'nav-')]/a";
		List<WebElement> catEntries = webDriver.findElements(By.xpath(catEntriesPath));
		Assert.assertNotEquals(0, catEntries.size());
		List<WebElement> displayedEntries = new ArrayList<WebElement>();
		Iterator<WebElement> itr = catEntries.iterator();
		while (itr.hasNext()) {
			WebElement entry = itr.next();
			if (entry.isDisplayed()) {
				displayedEntries.add(entry);
			}
		}
		Assert.assertTrue("Category '" + categoryName + "' has no displayed entries!", displayedEntries.size() > 0);
		// Click a random entry
		Integer entryIndex = 0;
		if (displayedEntries.size() > 1) {
			entryIndex = rnd.nextInt(displayedEntries.size() - 1);
		}
		WebElement entry = displayedEntries.get(entryIndex);
		entry.click();
		Assert.assertEquals(webDriver.getCurrentUrl(), entry.getAttribute("href"));
		
		// Close the category
		scrollNav(0);
		catHeader.click();
		Thread.sleep(1000);
		Assert.assertEquals(0, catItem.getSize().getHeight());
	}
	
	@Test
	public void listFilter() throws InterruptedException {
		String searchTerm = "set";
		WebElement search = webDriver.findElement(By.xpath("//input[@type='search']"));
		search.sendKeys(searchTerm);
		Thread.sleep(1000);
		// find categories with matching entries
		List<WebElement> hits = webDriver.findElements(By.xpath("//div[@id='list']/descendant::li[contains(@class, 'qx-tabs-button') and not(contains(@class, 'no-matches'))]"));
		Iterator<WebElement> itr = hits.iterator();
		while (itr.hasNext()) {
			WebElement hit = itr.next();
			hit.click();
			Thread.sleep(1000);
			WebElement hitCat = webDriver.findElement(By.xpath("//li[@id='list-group-" + hit.getText() + "']"));
			Assert.assertTrue(hitCat.getSize().getHeight() > 0);
			List<WebElement> matchingEntries = hitCat.findElements(By.xpath("ul[not(contains(@style, 'none'))]/li[not(contains(@style, 'none'))]/a"));
			Iterator<WebElement> entryItr = matchingEntries.iterator();
			int hitCount = Integer.parseInt(hit.getAttribute("data-results"));
			Assert.assertEquals(hitCount, matchingEntries.size());
			while (entryItr.hasNext()) {
				WebElement entry = entryItr.next();
				String entryLink = entry.getAttribute("href");
				Assert.assertTrue(entryLink.toLowerCase().contains(searchTerm));
			}
			
		}
	}
	
	@Test
	public void parameterLinks() {
		String qParamsPath = "//h4[text() = 'Parameters']/following-sibling::div/ul/li";
		WebElement qParams = webDriver.findElement(By.xpath(qParamsPath));
		List<WebElement> paramLinks = qParams.findElements(By.xpath("a"));
		Assert.assertTrue(paramLinks.size() > 0);
		Iterator<WebElement> itr = paramLinks.iterator();
		while (itr.hasNext()) {
			WebElement link = itr.next();
			Assert.assertTrue(link.getAttribute("href").startsWith("https://developer.mozilla.org"));
		}
	}
	
	@Test
	public void returnTypeLinks() throws InterruptedException {
		Map <String, String> mdnLinks = new HashMap<String, String>();
		mdnLinks.put("q.messaging.on", "String");
		mdnLinks.put("q.localStorage.getLength", "Number");
		mdnLinks.put(".getTransformBackfaceVisibility", "Boolean");
		mdnLinks.put("Array.every", "Array");
		mdnLinks.put("q.$getEventNormalizationRegistry", "Map");
		mdnLinks.put("q.define", "Function");
		Iterator itr = mdnLinks.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry pairs = (Map.Entry) itr.next();
			WebElement returnLink = webDriver.findElement(By.xpath("//div[@id='" + pairs.getKey() + "']/div[contains(@class, 'return-desc')]/descendant::a"));
			Assert.assertEquals("unexpected return type for '" + pairs.getKey() + "'", pairs.getValue(), returnLink.getText());
			Assert.assertTrue(returnLink.getAttribute("href").startsWith("https://developer.mozilla.org"));
			itr.remove();
		}
		
		WebElement returnLink = webDriver.findElement(By.xpath("//div[@id='.getAncestors']/div[contains(@class, 'return-desc')]/descendant::a"));
		Assert.assertEquals("unexpected return type for '.getAncestors'", "q", returnLink.getText());
		Assert.assertTrue(returnLink.getAttribute("href").endsWith("#Core"));
		
		returnLink = webDriver.findElement(By.xpath("//div[@id='q.io.xhr']/div[contains(@class, 'return-desc')]/descendant::a"));
		Assert.assertEquals("unexpected return type for '.getAncestors'", "Xhr", returnLink.getText());
		Assert.assertTrue(returnLink.getAttribute("href").endsWith("#Xhr"));
	}
	
	@Test
	public void jsFiddle() {
		List<WebElement> fiddleButtons = webDriver.findElements(By.className("fiddlebutton"));
		Assert.assertTrue(fiddleButtons.size() > 0);
		Random rnd = new Random();
		Integer btnIdx = rnd.nextInt(fiddleButtons.size() - 1);
		fiddleButtons.get(btnIdx).click();
		String initialHandle = webDriver.getWindowHandle();
		Set<String> handles = webDriver.getWindowHandles();
		Iterator<String> itr = handles.iterator();
		while (itr.hasNext()) {
			String handle = itr.next();
			if (!handle.equals(initialHandle)) {
				webDriver.switchTo().window(handle);
				Assert.assertEquals("http://jsfiddle.net/api/post/library/pure/", webDriver.getCurrentUrl());
				webDriver.close();
			}
		}
		webDriver.switchTo().window(initialHandle);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		webDriver.quit();
	}
}
