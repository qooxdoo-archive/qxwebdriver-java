package org.qooxdoo.demo.websiteunittests;

import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.resources.JavaScript;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qooxdoo.demo.Configuration;
import org.qooxdoo.demo.desktopunittests.DesktopUnitTests;

public class WebsiteUnitTests extends DesktopUnitTests {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Configuration.getQxWebDriver();
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
		new WebDriverWait(driver, 30, 250).until(testSuiteStateIs("ready"));
		String resPath = "/javascript/getTestClasses.js";
		JavaScript.INSTANCE.addResource("getTestClasses", resPath);
		testPackages = (List<String>) driver.jsRunner.runScript("getTestClasses");
		
		String skipPackages = System.getProperty("org.qooxdoo.demo.unittests.packages.skip", "");
		String[] skipPackagesArray = skipPackages.split(",");
		for (Iterator<String> iter = testPackages.listIterator(); iter.hasNext(); ) {
			String availablePackage = iter.next();
			for (String skipPackage: skipPackagesArray) {
				if (availablePackage.equals(skipPackage)) {
					iter.remove();
				}
			}
		}
	}
}
