package org.oneandone.qxwebdriver.ui;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface IWidget extends WebElement {
	
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
	 * Returns a {@link IWidget} representing a child control of this widget.
	 */
	public IWidget getChildControl(String childControlId);
	
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
	 * Returns a {@link IWidget} representing the value of a widget property,
	 * e.g. <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.MenuButton~menu!property">the 
	 * MenuButton's menu property</a>
	 */
	public IWidget getWidgetFromProperty(String propertyName);
	
	/**
	 * Returns a list of {@link IWidget} objects representing this widget's children
	 * as defined using <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.core.MChildrenHandling~add!method_public">parent.add(child);</a> in the application code.
	 */
	public List<IWidget> getChildren();
	
	/**
	 * Finds a widget relative to the current one by traversing the qooxdoo
	 * widget hierarchy.
	 */
	public IWidget findWidget(org.openqa.selenium.By by);

}
