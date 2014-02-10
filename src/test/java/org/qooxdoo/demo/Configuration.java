package org.qooxdoo.demo;

import java.net.MalformedURLException;
import java.net.URL;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Configuration {

	protected static DesiredCapabilities getCapabilities (String browserName) {
		DesiredCapabilities capabilities = null;
		
		if (browserName.equals("chrome")) {
			capabilities = DesiredCapabilities.chrome();
		}
		else if (browserName.equals("ie") || browserName.contains("explorer")) {
			capabilities = DesiredCapabilities.internetExplorer();
		}
		else if (browserName.equals("opera")) {
			capabilities = DesiredCapabilities.opera();
		}
		else if (browserName.equals("safari")) {
			capabilities = DesiredCapabilities.safari();
		}
		else if (browserName.equals("android")) {
			capabilities = DesiredCapabilities.android();
		}
		else if (browserName.equals("ipad")) {
			capabilities = DesiredCapabilities.ipad();
		}
		else if (browserName.equals("iphone")) {
			capabilities = DesiredCapabilities.iphone();
		}
		else if (browserName.equals("phantomjs")) {
			capabilities = DesiredCapabilities.phantomjs();
		}
		else {
			capabilities = DesiredCapabilities.firefox();
		}
		
		return capabilities;
	}
	
	protected static Platform getPlatform(String platformName) {
		Platform platform = null;
		if (platformName.equals("linux")) {
			platform = Platform.LINUX;
		}
		else if (platformName.equals("mac")) {
			platform = Platform.MAC;
		}
		else if (platformName.equals("xp")) {
			platform = Platform.XP;
		}
		else if (platformName.equals("win7")) {
			platform = Platform.VISTA;
		}
		else if (platformName.equals("win8")) {
			platform = Platform.WIN8;
		}
		else if (platformName.equals("windows")) {
			platform = Platform.WINDOWS;
		}
		else if (platformName.equals("android")) {
			platform = Platform.ANDROID;
		}
		else {
			platform = Platform.ANY;
		}
		
		return platform;
	}
	
	protected static WebDriver getWebDriver() throws MalformedURLException {
		WebDriver webDriver;
		String hubUrl = System.getProperty("org.qooxdoo.demo.huburl");
		//System.out.println("org.qooxdoo.demo.huburl: " + hubUrl);
		String browserName = System.getProperty("org.qooxdoo.demo.browsername", "firefox");
		//System.out.println("org.qooxdoo.demo.browsername: " + browserName);
		String browserVersion = System.getProperty("org.qooxdoo.demo.browserversion");
		String platformName = System.getProperty("org.qooxdoo.demo.platform", "any");
		//System.out.println("org.qooxdoo.demo.platform: " + platformName);
		
		if (hubUrl == null) {
			if (browserName.equals("chrome")) {
				webDriver = new ChromeDriver();
			} else {
				webDriver = new FirefoxDriver();
			}
		} else {
			DesiredCapabilities browser = getCapabilities(browserName);
			if (browserVersion != null) {
				//System.out.println("org.qooxdoo.demo.browserversion: " + browserVersion);
				browser.setVersion(browserVersion);
			}
			browser.setPlatform(getPlatform(platformName));
			webDriver = new RemoteWebDriver(new URL(hubUrl), browser);
		}
		return webDriver;
	}
	
	public static QxWebDriver getQxWebDriver() throws MalformedURLException {
		WebDriver webDriver = getWebDriver();
		QxWebDriver qxWebDriver = new QxWebDriver(webDriver);
		return qxWebDriver;
	}
}
