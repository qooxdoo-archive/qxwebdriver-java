package org.oneandone.qxwebdriver.ui;

import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * Represents a qx.Desktop widget. {@link org.openqa.selenium.WebElement}
 * methods are forwarded to the widget's content element. click() and sendKeys() 
 * will generally workFor simple widgets that contain only one button and/or 
 * text field.
 * 
 * For more advanced interactions on composite widgets such as qx.ui.formComboBox
 * or qx.ui.tree.Tree, see the other interfaces in this namespace.
 * 
 * @see Scrollable
 * @see Selectable
 *
 */
public interface Widget extends WebElement {
	
	/**
	 * This widget's qooxdoo object registry ID
	 */
	public String getQxHash();
	
	/**
	 * This widget's qooxdoo class name
	 */
	public String getClassname();
	
	/**
	 * The WebElement representing this widget's content element
	 */
	public WebElement getContentElement();
	
	/**
	 * Returns a {@link Widget} representing a child control of this widget.
	 */
	public Widget getChildControl(String childControlId);
	
	//TODO doc
	public Object executeJavascript(String script);
	
	/**
	 * Returns the value of a qooxdoo property on this widget, serialized in JSON
	 * format.
	 * <strong>NOTE:</strong> Never use this for property values that are instances
	 * of qx.core.Object. Circular references in qooxoo's OO system will lead to
	 * JavaScript errors.
	 */
	public String getPropertyValueAsJson(String propertyName);
	
	/**
	 * Returns the value of a qooxdoo property on this widget. See the {@link org.openqa.selenium.JavascriptExecutor}
	 * documentation for details on how JavaScript types are represented.
	 * <strong>NOTE:</strong> Never use this for property values that are instances
	 * of qx.core.Object. Circular references in qooxoo's OO system will lead to
	 * JavaScript errors.
	 */
	public Object getPropertyValue(String propertyName);
	
	/**
	 * Returns a {@link Widget} representing the value of a widget property,
	 * e.g. <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.MenuButton~menu!property">the 
	 * MenuButton's menu property</a>
	 */
	public Widget getWidgetFromProperty(String propertyName);
	
	/**
	 * Returns a list of {@link Widget} objects representing this widget's children
	 * as defined using <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.core.MChildrenHandling~add!method_public">parent.add(child);</a> in the application code.
	 */
	public List<Widget> getChildren();
	
	/**
	 * Finds a widget relative to the current one by traversing the qooxdoo
	 * widget hierarchy.
	 */
	public Widget findWidget(org.openqa.selenium.By by);

}
