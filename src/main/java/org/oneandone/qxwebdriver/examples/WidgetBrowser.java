package org.oneandone.qxwebdriver.examples;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.examples.widgetbrowser.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WidgetBrowser {
	
	public static final String defaultUrl = "http://demo.qooxdoo.org/current/widgetbrowser/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QxWebDriver driver = new QxWebDriver(new FirefoxDriver());
		//driver = new QxWebDriver(new ChromeDriver());
		driver.get(defaultUrl);
		
		Form form = new Form(driver);
		form.test();
		
		/*
		Tree tree = new Tree(driver);
		tree.test();
		
		List list = new List(driver);
		list.test();
		
		ToolbarMenu toolbarMenu = new ToolbarMenu(driver);
		toolbarMenu.test();
		*/
		
		driver.close();
	}

}
