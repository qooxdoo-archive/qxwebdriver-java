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
		By treeLocator = By.qxh("qx.ui.container.Composite/qx.ui.splitpane.Pane/qx.ui.tree.Tree");
		Tree tree = (Tree) driver.findWidget(treeLocator);
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
		String articleLoc = "qx.ui.container.Composite/qx.ui.splitpane.Pane/qx.ui.splitpane.Pane/feedreader.view.desktop.Article";
		Widget article = driver.findWidget(By.qxh(articleLoc));
		String html = (String) article.getPropertyValue("html");
		Assert.assertNotEquals(articleLength, html.length());
	}

}
