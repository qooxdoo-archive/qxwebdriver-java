package org.oneandone.qxwebdriver.examples.widgetbrowser;

import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;

/**
 * Tests for the Widget Browser's Form tab
 *
 */
public class Form extends WidgetBrowser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Form form = new Form(); 
		form.test();
		System.out.println("All tests done.");
	}
	
	public void test() {
		selectTab("Form");
		textFields();
		comboBox();
		virtualComboBox();
		dateField();
		selectBox();
		virtualSelectBox();
		list();
		radioButtonGroup();
		buttons();
	}

	public void textFields() {
		By textFieldLocator = By.qxh("*/qx.ui.form.TextField");
		Widget textField = tabPage.findWidget(textFieldLocator);
		textField.sendKeys("Hello TextField");
		
		By passwordFieldLocator = By.qxh("*/qx.ui.form.PasswordField");
		Widget passwordField = tabPage.findWidget(passwordFieldLocator);
		passwordField.sendKeys("Hello PasswordField");
		
		By textAreaLocator = By.qxh("*/qx.ui.form.TextArea");
		Widget textArea = tabPage.findWidget(textAreaLocator);
		textArea.sendKeys("Hello\nTextArea");
	}
	
	public void comboBox() {
		By comboBoxLocator = By.qxh("*/qx.ui.form.ComboBox");
		// The selectItem clicks the ComboBox, then clicks an item in the popup list 
		Selectable comboBox = (Selectable) tabPage.findWidget(comboBoxLocator);
		comboBox.selectItem("Item 4");
		// clear is delegated to the TextField child control when using the
		// Selectable interface
		comboBox.clear();
		comboBox.sendKeys("Hello ComboBox");
	}
	
	public void virtualComboBox() {
		Selectable vComboBox = (Selectable) tabPage.findWidget(By.qxh("*/qx.ui.form.VirtualComboBox"));
		vComboBox.selectItem("Item 14");
		
		vComboBox.clear();
		vComboBox.sendKeys("Hello VirtualComboBox");
	}
	
	public void dateField() {
		Widget dateField = tabPage.findWidget(By.qxh("*/qx.ui.form.DateField"));
		// No Selectable implementation for DateField yet, so we use the childControls directly
		dateField.getChildControl("button").click();
		Widget list = dateField.waitForChildControl("list", 2);
		list.getChildControl("next-year-button").click();
		list.getChildControl("last-month-button").click();
		list.findWidget(By.qxh("*/[@value=^12$]")).click();
	}
	
	public void selectBox() {
		Selectable selectBox = (Selectable) tabPage.findWidget(By.qxh("*/qx.ui.form.SelectBox"));
		selectBox.selectItem("Item 4");
	}
	
	public void virtualSelectBox() {
		Selectable vSelectBox = (Selectable) tabPage.findWidget(By.qxh("*/qx.ui.form.VirtualSelectBox"));
		vSelectBox.selectItem("Item 14");
	}
	
	public void list() {
		Selectable list = (Selectable) tabPage.findWidget(By.qxh("*/qx.ui.form.List"));
		list.selectItem("Item 5");
	}
	
	public void radioButtonGroup() {
		By by = By.qxh("*/[@label=RadioButton 2]");
		Widget radioButton = tabPage.findWidget(by);
		radioButton.click();
		boolean isSelected = radioButton.isSelected();
		if (!isSelected) {
			throw new RuntimeException("RadioButton not selected after click!");
		}
	}
	
	public void buttons() {
		Widget toggleButton = tabPage.findWidget(By.qxh("*/qx.ui.form.ToggleButton"));
		boolean selectedInitial = toggleButton.isSelected();
		if (selectedInitial) {
			throw new RuntimeException("ToggleButton already selected.");
		}
		toggleButton.click();
		boolean selected = toggleButton.isSelected();
		if (!selected) {
			throw new RuntimeException("ToggleButton not selected after click.");
		}
		
		Selectable menuButton = (Selectable) tabPage.findWidget(By.qxh("*/qx.ui.form.MenuButton"));
		menuButton.selectItem("Button2");
	}
	
}
