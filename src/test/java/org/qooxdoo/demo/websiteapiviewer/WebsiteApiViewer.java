package org.qooxdoo.demo.websiteapiviewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.qooxdoo.demo.Configuration;
import org.qooxdoo.demo.IntegrationTest;

public class WebsiteApiViewer extends IntegrationTest {
	
	public static WebDriver webDriver;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		webDriver = Configuration.getWebDriver();
		webDriver.manage().window().maximize();
		webDriver.get(System.getProperty("org.qooxdoo.demo.auturl"));
		Thread.sleep(2000);
	}
	
	@Test
	public void syntaxHighlighting() {
		List<WebElement> samples = webDriver.findElements(By.className("javascript"));
		Assert.assertNotEquals(0, samples.size());
		Iterator<WebElement> itr = samples.iterator();
		while (itr.hasNext()) {
			WebElement sample = itr.next();
			List<WebElement> strings = sample.findElements(By.tagName("span"));
			Assert.assertTrue(strings.size() > 0);
		}
	}
	
	public List<String> getCategories() {
		List<WebElement> accordionButtons = webDriver.findElements(By.xpath("//div[@id='list']/ul/li[contains(@class, 'qx-accordion-button')]"));
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
		Integer catIndex = rnd.nextInt(categories.size());
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
		
		// Click a random entry
		Integer entryIndex = rnd.nextInt(displayedEntries.size());
		WebElement entry = displayedEntries.get(entryIndex);
		entry.click();
		Assert.assertEquals(webDriver.getCurrentUrl(), entry.getAttribute("href"));
		
		// Close the category 
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
		List<WebElement> hits = webDriver.findElements(By.xpath("//div[@id='list']/descendant::li[contains(@class, 'qx-accordion-button') and not(contains(@class, 'no-matches'))]"));
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
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		webDriver.quit();
	}
}
