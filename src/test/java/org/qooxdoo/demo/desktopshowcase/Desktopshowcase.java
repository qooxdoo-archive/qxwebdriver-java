package org.qooxdoo.demo.desktopshowcase;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.oneandone.qxwebdriver.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qooxdoo.demo.IntegrationTest;

public abstract class Desktopshowcase extends IntegrationTest {
	
	public String qxVersion = null;

	public void selectPage(String title) {
		qxVersion = (String) driver.executeScript("return qx.core.Environment.get('qx.version')");
		WebElement root = driver.findElement(By.id("showcase"));
		By locator = By.qxh("*/showcase.ui.PreviewList/*/[@label=" + title + "]");
		WebElement item = root.findElement(locator);
		item.click();
		waitUntilPageLoaded();
		checkLinks();
	}
	
	public WebElement getRoot() {
		return driver.findElement(By.id("showcase"));
	}
	
	public Boolean isLoading() {
		By locator = By.qxh("qx.ui.container.Stack/qx.ui.container.Composite/[@source=loading]", false);
		WebElement spinner = getRoot().findElement(locator);
		return spinner.isDisplayed();
	}
	
	public ExpectedCondition<Boolean> showcaseLoaded() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return !isLoading();
			}

			@Override
			public String toString() {
				return "Showcase page has finished loading.";
			}
		};
	}
	
	public void waitUntilPageLoaded() {
		new WebDriverWait(driver, 20, 250).until(showcaseLoaded());
	}
	
	public void checkLinks() {
		String initialHandle = driver.getWindowHandle();
		List<WebElement> links = driver.findElements(By.xpath("//div[@id='showcase']/descendant::div[@id='description']/descendant::a"));
		Iterator<WebElement> iter = links.iterator();
		while (iter.hasNext()) {
			WebElement link = iter.next();
			String linkText = link.getText();
			String href = link.getAttribute("href");
			link.click();
			Set<String> handles = driver.getWindowHandles();
			Assert.assertEquals(2, handles.size());
			Iterator<String> itr = handles.iterator();
			while (itr.hasNext()) {
				String handle = itr.next();
				if (!handle.equals(initialHandle)) {
					driver.switchTo().window(handle);
					if (!linkText.equals("YQL")) {
						String newUrl = driver.getCurrentUrl();
						Assert.assertEquals("Link " + linkText + " did not open URI " + href, href, newUrl);
						Assert.assertTrue("Link " + linkText + " does not point to qx version " + qxVersion, qxVersionMatches(newUrl));
					}
					driver.close();
				}
			}
			driver.switchTo().window(initialHandle);
		}

	}
	
	public Boolean qxVersionMatches(String uri) {
		String[] split0 = uri.split("\\.org\\/");
		String[] split1 = split0[1].split("\\/");
		return split1[0].equals(qxVersion);
	}
}
