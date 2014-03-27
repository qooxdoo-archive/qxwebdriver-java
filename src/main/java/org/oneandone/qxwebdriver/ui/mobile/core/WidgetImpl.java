/* ************************************************************************

   qxwebdriver-java

   http://github.com/qooxdoo/qxwebdriver-java

   Copyright:
     2014 1&1 Internet AG, Germany, http://www.1und1.de

   License:
     LGPL: http://www.gnu.org/licenses/lgpl.html
     EPL: http://www.eclipse.org/org/documents/epl-v10.php
     See the license.txt file in the project's top-level directory for details.

   Authors:
     * Daniel Wagner (danielwagner)

************************************************************************ */

package org.oneandone.qxwebdriver.ui.mobile.core;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Touchable;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;


public class WidgetImpl extends org.oneandone.qxwebdriver.ui.core.WidgetImpl implements Touchable {

	public WidgetImpl(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
		// workaround for https://github.com/selendroid/selendroid/issues/337
		contentElement = element;
	}
	
	public void tap() {
		TouchActions tap = new TouchActions(driver.getWebDriver()).singleTap(contentElement);
		tap.perform();
	}
	
	public void track(int x, int y, int step) {
		track(driver.getWebDriver(), contentElement, x, y, step);
	}
	
	public static void track(WebDriver driver, WebElement element, int x, int y, int step) {
		if (step == 0) {
			step = 1;
		}
		Dimension size = element.getSize();
		int halfWidth = size.getWidth() / 2;
		int halfHeight = size.getHeight() / 2;

		Point loc = element.getLocation();
		int startX = loc.getX();
		int startY = loc.getY();

		int posX = startX + halfWidth;
		int posY = startY + halfHeight;

		int endX = posX + x;
		int endY = posY + y;
		TouchActions action = new TouchActions(driver);
		action.down(posX, posY);
		while (posX < endX || posY < endY) {
			if (posX < endX) {
				if (posX + step > endX) {
					posX += endX - (posX + step);
				} else {
					posX += step;
				}
			}
			if (posY < endY) {
				if (posY + step > endY) {
					posY += endY - (posY + step);
				} else {
					posY += step;
				}
			}
			action.move(posX, posY);
		}
		action.up(posX, posY)
		.perform();
	}
	

	public boolean isDisplayed() {
		if (contentElement == null) {
			return false;
		}
		return (Boolean) executeJavascript("return qxwebdriver.getWidgetByElement(arguments[0]).isVisible()");
	}

}
