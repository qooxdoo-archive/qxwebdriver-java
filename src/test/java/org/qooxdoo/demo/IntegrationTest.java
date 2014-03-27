package org.qooxdoo.demo;

import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.log.LogEntry;

public abstract class IntegrationTest {
	
	public static QxWebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Configuration.getQxWebDriver();
		driver.manage().window().maximize();
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
		driver.registerLogAppender();
		driver.registerGlobalErrorHandler();
	}
	
	/**
	 * Prints the AUT's log messages
	 */
	public static void printQxLog(QxWebDriver driver) {
		List<LogEntry> logEntries = driver.getLogEvents();
		Iterator<LogEntry> logItr = logEntries.iterator();
		while (logItr.hasNext()) {
			System.out.println(logItr.next());
		}
	}
	
	/**
	 * Prints AUT exceptions
	 */
	public static void printQxErrors(QxWebDriver driver) {
		List<String> caughtErrors = (List<String>) driver.getCaughtErrors();
		Iterator exItr = caughtErrors.iterator();
		while (exItr.hasNext()) {
			System.err.println(exItr.next());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		printQxLog(driver);
		printQxErrors(driver);
		driver.quit();
	}
}
