package org.qooxdoo.demo.feedreaderdesktop;

import java.util.Iterator;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.basic.Label;
import org.oneandone.qxwebdriver.ui.form.List;
import org.oneandone.qxwebdriver.ui.tree.Tree;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qooxdoo.demo.IntegrationTest;

public class FeedReader extends IntegrationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		IntegrationTest.setUpBeforeClass();
		driver.jsExecutor.executeScript("qx.locale.Manager.getInstance().setLocale('en');");
	}

	public static String toolbarLocator = "qx.ui.container.Composite/feedreader.view.desktop.ToolBar";
	public static String prefWinLocator = "feedreader.view.desktop.PreferenceWindow";
	public static String treeLocator = "qx.ui.container.Composite/qx.ui.splitpane.Pane/qx.ui.tree.Tree";
	public static String articleLoc = "qx.ui.container.Composite/qx.ui.splitpane.Pane/qx.ui.splitpane.Pane/feedreader.view.desktop.Article";
	public static String addFeedLoc = "feedreader.view.desktop.AddFeedWindow";
	
	@Before
	public void waitUntilFeedsLoaded() {
		new WebDriverWait(driver, 30, 250).until(feedsReady());
	}

	private List __postList;

	public List getPostList() {
		if (__postList == null) {
			String listLoc = "qx.ui.container.Composite/qx.ui.splitpane.Pane/qx.ui.splitpane.Pane/feedreader.view.desktop.List";
			Widget frList = (Widget) driver.findWidget(By.qxh(listLoc));
			WebElement listEl = (WebElement) frList.executeJavascript("return qx.ui.core.Widget.getWidgetByElement(arguments[0]).getList().getContentElement().getDomElement()");
			List list = (List) driver.getWidgetForElement(listEl);
			__postList = list;
		}
		
		return __postList;
	}
	
	public ExpectedCondition<Boolean> feedsReady() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				By treeLoc = By.qxh(treeLocator);
				Tree tree = (Tree) driver.findWidget(treeLoc);
				java.util.List<Widget> items = (java.util.List<Widget>) tree.getWidgetListFromProperty("items");
				Iterator<Widget> itr = items.iterator();
				while (itr.hasNext()) {
					Widget item = (Widget) itr.next();
					String iconSrc = (String) item.getChildControl("icon").getPropertyValue("source");
					if (iconSrc.contains("loading")) {
						return false;
					}
				}
				return true;
			}

			@Override
			public String toString() {
				return "All feeds have finished loading.";
			}
		};
	}

	@Test
	public void feedsLoaded() {
		By treeLoc = By.qxh(treeLocator);
		Tree tree = (Tree) driver.findWidget(treeLoc);
		java.util.List<Widget> items = (java.util.List<Widget>) tree.getWidgetListFromProperty("items");
		Iterator<Widget> itr = items.iterator();
		while (itr.hasNext()) {
			Widget item = (Widget) itr.next();
			Label label = (Label) item.getChildControl("label");
			String feedTitle = label.getValue();
			String iconSrc = (String) item.getChildControl("icon").getPropertyValue("source");
			if (iconSrc.contains("process-stop")) {
				System.err.println("Feed '" + feedTitle + "' did not load!");
			} else if (!iconSrc.contains("folder")) {
				checkFeed(item);
			}
		}
	}

	public String escapeJsRegEx(String str) {
		String result = str.replaceAll("([()\\[{*+.$^\\|?])", "\\\\$1");
		return result;
	}

	public void checkFeed(Widget item) {
		String title = item.getText();
		System.out.println("Checking feed " + title);
		Assert.assertNotNull("item is null", item);
		item.click();
		List postList = getPostList();
		java.util.List<Widget> items = postList.getChildren();
		Assert.assertNotEquals("Feed '" + title + "' has no entries.", items.size(), 0);
		Random rnd = new Random();
		Integer feedIndex = rnd.nextInt(items.size());
		Widget listItem  = items.get(feedIndex);
		Assert.assertNotNull("list item is null", listItem);
		String newItemLabel = (String) listItem.getPropertyValue("label");
		Assert.assertNotNull("new item label is null", newItemLabel);
		// scroll the feed item into view
		Widget feedItem = postList.getSelectableItem("^" + escapeJsRegEx(newItemLabel) + "$");
		if (feedItem == null) {
			System.err.println("Feed item '" + newItemLabel +  "' is null");
			return;
		}
		String label = (String) feedItem.getPropertyValue("label");
		Assert.assertNotNull(label);
		Assert.assertEquals(newItemLabel, label);
		feedItem.click();
		checkFeedItem();
	}

	public void checkFeedItem() {
		Widget article = driver.findWidget(By.qxh(articleLoc));
		String html = (String) article.getPropertyValue("html");
		Assert.assertNotEquals(0, html.length());
	}

	@Test
	public void changeLocale() throws InterruptedException {
		selectLocale("Italiano");
		Thread.sleep(500);
		// translate a string (only works if the locale was loaded correctly)
		String preferences = driver.getTranslation("Preferences");
		Assert.assertEquals("Preferenze", preferences);

		String prefsLocator = toolbarLocator + "/[@label=" + preferences + "]";
		Widget prefsButton = driver.findWidget(By.qxh(prefsLocator));
		Assert.assertNotNull(prefsButton);

		String folderLocator = treeLocator + "/child[0]/child[0]/child[0]/qx.ui.tree.TreeFolder/[@value=Feed statici]";
		Widget treeFolder = driver.findWidget(By.qxh(folderLocator));
		Assert.assertNotNull(treeFolder);

		selectLocale("English");

		folderLocator = treeLocator + "/child[0]/child[0]/child[0]/qx.ui.tree.TreeFolder/[@value=Static Feeds]";
		treeFolder = driver.findWidget(By.qxh(folderLocator));
		Assert.assertNotNull(treeFolder);
	}
	
	public void selectLocale(String language) {
		String preferences = driver.getTranslation("Preferences");
		String prefsLocator = toolbarLocator + "/[@label=" + preferences + "]";
		Widget prefsButton = driver.findWidget(By.qxh(prefsLocator));
		prefsButton.click();

		String italianLocator = prefWinLocator + "/*/[@label=" + language + "]";
		Widget italianLabel = driver.findWidget(By.qxh(italianLocator));
		italianLabel.click();

		String ok = driver.getTranslation("OK");
		String okLocator = prefWinLocator + "/qx.ui.container.Composite/[@label=" + ok + "]";
		Widget okButton = driver.findWidget(By.qxh(okLocator));
		okButton.click();
	}
	
	@Test
	public void addFeed() throws InterruptedException {
		String newFeedTitle = "The Register";
		String newFeedUrl = "http://www.theregister.co.uk/headlines.atom";
		
		String addFeed = driver.getTranslation("Add feed");
		String addWinLocator = toolbarLocator + "/[@label=" + addFeed + "]";
		Widget addWinButton = driver.findWidget(By.qxh(addWinLocator));
		addWinButton.click();
		
		String title = driver.getTranslation("Title");
		Widget titleInput = driver.findWidget(By.qxh(addFeedLoc + "/*/[@placeholder=" + title + "]"));
		titleInput.sendKeys(newFeedTitle);
		
		String url = driver.getTranslation("URL");
		Widget urlInput = driver.findWidget(By.qxh(addFeedLoc + "/*/[@placeholder=" + url + "]"));
		urlInput.sendKeys(newFeedUrl);
		
		String add = driver.getTranslation("Add");
		Widget addButton = driver.findWidget(By.qxh(addFeedLoc + "/*/[@label=" + add + "]"));
		addButton.click();
		
		Widget newFeedItem = driver.waitForWidget(By.qxh(treeLocator + "/*/[@label=" + newFeedTitle + "]"), 15);
		Assert.assertNotNull(newFeedItem);
		waitUntilFeedsLoaded();
		checkFeed(newFeedItem);
	}

}
