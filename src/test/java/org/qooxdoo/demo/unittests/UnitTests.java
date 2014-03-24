package org.qooxdoo.demo.unittests;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.resources.JavaScript;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qooxdoo.demo.Configuration;
import org.qooxdoo.demo.IntegrationTest;

public class UnitTests extends IntegrationTest {
	
	public static String getTestSuiteState = "return qx.core.Init.getApplication().runner.getTestSuiteState();";
	public static String getTestResults = "return JSON.stringify(qx.core.Init.getApplication().runner.view.getTestResults());";
	
	public static List<String> testPackages;
	
	protected Integer failCount = 0;
	
	public static ExpectedCondition<Boolean> testSuiteStateIs(final String state) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				String result = null;
				try {
					result = getSuiteState();
					return result.equals(state);
				} catch(org.openqa.selenium.WebDriverException e) {
					System.err.println("Couldn't get test suite state: " + e.toString());
					return false;
				}
			}

			@Override
			public String toString() {
				return "Test suite state is '" + state + "'.";
			}
		};
	}
	
	public static String getSuiteState() {
		String suiteState = (String) driver.jsExecutor.executeScript(getTestSuiteState);
		return suiteState;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = Configuration.getQxWebDriver();
		driver.get(System.getProperty("org.qooxdoo.demo.auturl"));
		new WebDriverWait(driver, 30, 250).until(testSuiteStateIs("ready"));
		String resPath = "/javascript/getTestPackages.js";
		JavaScript.INSTANCE.addResource("getTestPackages", resPath);
		testPackages = (List<String>) driver.jsRunner.runScript("getTestPackages");
	}
	
	@Test
	public void unitTests() {
		Iterator<String> itr = testPackages.iterator();
		while (itr.hasNext()) {
			String nextPackage = itr.next();
			runPackage(nextPackage);
			getResults();
			logAutExceptions();
		}
		System.out.println("All test packages completed.");
		Assert.assertEquals(failCount + " test(s) failed.", Double.valueOf(0), Double.valueOf(failCount));
	}
	
	public void runPackage(String packageName) {
		String packageUrl = System.getProperty("org.qooxdoo.demo.auturl") + "?testclass=" + packageName;
		System.out.println("Executing test package " + packageName);
		driver.get(packageUrl);
		driver.registerGlobalErrorHandler();
		new WebDriverWait(driver, 30, 250).until(testSuiteStateIs("ready"));
		driver.executeScript("qx.core.Init.getApplication().runner.view.run()");
		new WebDriverWait(driver, 600, 250).until(testSuiteStateIs("finished"));
	}
	
	public void getResults() {
		System.out.println("Retrieving package results.");
		String results = (String) driver.executeScript(getTestResults);
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(results);
			JSONObject jsonEntry = (JSONObject) obj;
			Set set = jsonEntry.keySet();
			Iterator itr = set.iterator();
			while (itr.hasNext()) {
				String testName = (String) itr.next();
				JSONObject testResult = (JSONObject) jsonEntry.get(testName);
				String state = (String) testResult.get("state");
				if (state.equals("error") || state.equals("failure")) {
					failCount++;
					System.err.println(state.toUpperCase() + " " + testName);
					JSONArray messages = (JSONArray) testResult.get("messages");
					Iterator<String> mItr = messages.iterator();
					while (mItr.hasNext()) {
						String message = mItr.next();
						System.err.println(message);
					}
					System.err.println();
				}
			}
		} catch (ParseException e) {
			System.err.println("Unable to parse JSON test results " + results);
			e.printStackTrace();
		}
	}
	
	public void logAutExceptions() {
		// Print AUT exceptions
		List<String> caughtErrors = (List<String>) driver.getCaughtErrors();
		Iterator<String> exItr = caughtErrors.iterator();
		while (exItr.hasNext()) {
			System.err.println(exItr.next());
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

}
