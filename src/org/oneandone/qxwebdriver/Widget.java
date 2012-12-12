package org.oneandone.qxwebdriver;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface Widget {

	String qxHash = null;
	WebElement contentElement = null;
	WebDriver driver = null;
	JavascriptExecutor jsExecutor = null;
	
	
	void click();
	
	void sendKeys(CharSequence keysToSend);
	
	void waitForChildControl(String childControlId, Integer timeout);
	
	WebElement getChildControl(String childControlId);
	
	List<WebElement> getChildren();
	
	WebElement getChild(String text);
	
	String getSerializedPropertyValue(String propertyName);
	
	WebElement getElementFromProperty(String propertyName);
	
	WebElement findElement(By by);
}
