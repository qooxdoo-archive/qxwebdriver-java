package org.qooxdoo.demo.feedreader.desktop;

import java.util.Iterator;
import java.util.Random;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.basic.Label;
import org.oneandone.qxwebdriver.ui.form.List;
import org.oneandone.qxwebdriver.ui.tree.Tree;
import org.openqa.selenium.WebElement;
import org.qooxdoo.demo.IntegrationTest;

public class FeedReader extends IntegrationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("org.qooxdoo.demo.auturl",
				"http://demo.qooxdoo.org/current/feedreader/index.html");
		IntegrationTest.setUpBeforeClass();
	}

	public static String toolbarLocator = "qx.ui.container.Composite/feedreader.view.desktop.ToolBar";
	public static String prefWinLocator = "feedreader.view.desktop.PreferenceWindow";
	public static String treeLocator = "qx.ui.container.Composite/qx.ui.splitpane.Pane/qx.ui.tree.Tree";
	public static String articleLoc = "qx.ui.container.Composite/qx.ui.splitpane.Pane/qx.ui.splitpane.Pane/feedreader.view.desktop.Article";
	public static String addFeedLoc = "feedreader.view.desktop.AddFeedWindow";

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

	@Test
	public void feedsLoaded() {
		By treeLoc = By.qxh(treeLocator);
		Tree tree = (Tree) driver.findWidget(treeLoc);
		java.util.List<Widget> items = (java.util.List<Widget>) tree.getWidgetListFromProperty("items");
		Iterator<Widget> itr = items.iterator();
		while (itr.hasNext()) {
			Widget item = (Widget) itr.next();
			String iconSrc = (String) item.getChildControl("icon").getPropertyValue("source");
			if (iconSrc.contains("process-stop")) {
				Label label = (Label) item.getChildControl("label");
				System.err.println("Feed '" + label.getValue() + "' did not load!");
			} else if (!iconSrc.contains("folder")) {
				checkFeed(item);
			}
		}
	}

	public String escapeJsRegEx(String str) {
		String result = str.replaceAll("([()\\[{*+.$^\\|?])", "\\\\$1");
		return result;
	}

	private String itemLabel;

	public void checkFeed(Widget item) {
		item.click();
		List postList = getPostList();
		java.util.List<Widget> items = postList.getChildren();
		Random rnd = new Random();
		Integer feedIndex = rnd.nextInt(items.size());
		Widget listItem  = items.get(feedIndex);
		String newItemLabel = (String) listItem.getPropertyValue("label");
		Assert.assertNotEquals(itemLabel, newItemLabel);
		// scroll the feed item into view
		Widget feedItem = postList.getSelectableItem(escapeJsRegEx(newItemLabel));
		Assert.assertEquals(newItemLabel, (String) feedItem.getPropertyValue("label"));
		feedItem.click();
		checkFeedItem();
	}

	private int articleLength;

	public void checkFeedItem() {
		Widget article = driver.findWidget(By.qxh(articleLoc));
		String html = (String) article.getPropertyValue("html");
		Assert.assertNotEquals(articleLength, html.length());
	}

	@Test
	public void changeLocale() {
		selectLocale("Italiano");

		// The label's string representation is *not* translated, so we look for the label's value instead
		String prefsLocator = toolbarLocator + "/*/[@value=Preferenze]";
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
		String prefsLocator = toolbarLocator + "/[@label=Preferences]";
		Widget prefsButton = driver.findWidget(By.qxh(prefsLocator));
		prefsButton.click();

		String italianLocator = prefWinLocator + "/*/[@label=" + language + "]";
		Widget italianLabel = driver.findWidget(By.qxh(italianLocator));
		italianLabel.click();

		String okLocator = prefWinLocator + "/qx.ui.container.Composite/[@label=OK]";
		Widget okButton = driver.findWidget(By.qxh(okLocator));
		okButton.click();
	}
	
	@Test
	public void addFeed() {
		String newFeedTitle = "The Register";
		String newFeedUrl = "http://www.theregister.co.uk/headlines.atom";
		
		String addWinLocator = toolbarLocator + "/[@label=Add feed]";
		Widget addWinButton = driver.findWidget(By.qxh(addWinLocator));
		addWinButton.click();
		
		Widget titleInput = driver.findWidget(By.qxh(addFeedLoc + "/*/[@placeholder=Title]"));
		titleInput.sendKeys(newFeedTitle);
		
		Widget urlInput = driver.findWidget(By.qxh(addFeedLoc + "/*/[@placeholder=URL]"));
		urlInput.sendKeys(newFeedUrl);
		
		Widget addButton = driver.findWidget(By.qxh(addFeedLoc + "/*/[@label=Add]"));
		addButton.click();
		
		Widget newFeedItem = driver.findWidget(By.qxh(treeLocator + "/*/[@label=" + newFeedTitle + "]"));
		Assert.assertNotNull(newFeedItem);
		checkFeed(newFeedItem);
	}

}
