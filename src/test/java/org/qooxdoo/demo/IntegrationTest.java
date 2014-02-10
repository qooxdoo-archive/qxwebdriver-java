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

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// Print the AUT's log messages
		List<LogEntry> logEntries = driver.getLogEvents();
		Iterator<LogEntry> logItr = logEntries.iterator();
		while (logItr.hasNext()) {
			System.out.println(logItr.next());
		}
		
		// Print AUT exceptions
		List<String> caughtErrors = (List<String>) driver.getCaughtErrors();
		Iterator exItr = caughtErrors.iterator();
		while (exItr.hasNext()) {
			System.err.println(exItr.next());
		}
		
		//driver.close();
		driver.quit();
	}
}
