package org.oneandone.qxwebdriver.ui;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;

import org.oneandone.qxwebdriver.ui.Widget;
import org.openqa.selenium.WebElement;

public class DefaultWidgetFactory implements org.oneandone.qxwebdriver.ui.WidgetFactory {

	public DefaultWidgetFactory(QxWebDriver qxWebDriver) {
		driver = qxWebDriver;
		packageName = this.getClass().getPackage().getName();
	}
	
	protected QxWebDriver driver;
	private String packageName;
	
	/**
	 * Returns an instance of {@link Widget} or one of its subclasses that
	 * represents the qooxdoo widget containing the given element.
	 * @param element A WebElement representing a DOM element that is part of a
	 * qooxdoo widget
	 * @return Widget object
	 */
	public Widget getWidgetForElement(WebElement element, List<String> classes) {
		
		if (classes.remove("qx.ui.core.Widget")) {
			classes.add("qx.ui.core.WidgetImpl");
		}
		
		Iterator<String> classIter = classes.iterator();
		
		while(classIter.hasNext()) {
			String className = classIter.next();
			String widgetClassName = getWidgetClassName(className);
			if (widgetClassName != null) {
				Constructor<?> constr = getConstructorByClassName(widgetClassName);
				if (constr != null) {
					try {
						Widget widget = (Widget) constr.newInstance(element, driver);
						return widget;
					} catch(Exception e) {
						System.err.println("Could not instantiate '" + 
								widgetClassName + "': " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}
	
	private String getWidgetClassName(String qxClassName) {
		if (qxClassName.startsWith("qx.ui.")) {
			return packageName + qxClassName.substring(5);
		} else {
			return null;
		}
	}
	
	private Constructor<?> getConstructorByClassName(String widgetClassName) {
		Constructor<?> cnst[];
		try {
			Class<?> widgetClass = Class.forName(widgetClassName);
			cnst = widgetClass.getConstructors();
			if (cnst.length > 0) {
				return cnst[0];
			}
		} catch(ClassNotFoundException e) {
			//System.out.println("No class for " + widgetClassName);
		}
		return null;
	}

}
