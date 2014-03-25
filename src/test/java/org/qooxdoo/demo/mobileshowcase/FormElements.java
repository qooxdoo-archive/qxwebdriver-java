package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Widget;

public class FormElements extends Mobileshowcase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		selectItem("Form Elements");
		verifyTitle("Form");
	}
	
	@Test
	public void textField() {
		Widget userName = driver.findWidget(By.qxh("*/qx.ui.mobile.form.TextField"));
		userName.sendKeys("Affe");
		Assert.assertEquals("Affe", userName.getPropertyValue("value"));
	}
}
