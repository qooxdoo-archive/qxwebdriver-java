package org.qooxdoo.demo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class OnFailed extends TestWatcher {
	
	/**
	 * Takes a screenshot if a test fails.
	 */
	@Override
	protected void failed(Throwable e, Description description) {
		String autName = System.getProperty("org.qooxdoo.demo.autname");
		String browserName = System.getProperty("org.qooxdoo.demo.browsername");
		String browserVersion = System.getProperty("org.qooxdoo.demo.browserversion");
		String platformName = System.getProperty("org.qooxdoo.demo.platform");
		long now = System.currentTimeMillis();
		String fileName = String.valueOf(now) + " " + autName + " " + browserName + " " + browserVersion + " " + platformName + ".png";
		String tempDir = System.getProperty("java.io.tmpdir");
		String path = tempDir + "/" + fileName;
		
		File scrFile = ((TakesScreenshot)IntegrationTest.driver.getWebDriver()).getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e1) {
			System.err.println("Couldn't save screenshot: " + e1.getMessage());
			e1.printStackTrace();
		}
		System.out.println("Saved screenshot as " + path);
	}
}