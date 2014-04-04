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

import java.util.Iterator;
import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Touchable;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.Locatable;


public class WidgetImpl extends org.oneandone.qxwebdriver.ui.core.WidgetImpl implements Touchable {

	public WidgetImpl(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
		// workaround for https://github.com/selendroid/selendroid/issues/337
		contentElement = element;
	}
	
	public boolean isDisplayed() {
		if (contentElement.isDisplayed()) {
			String script = "return arguments[0].offsetWidth > 0 || arguments[0].offsetHeight > 0";
			return (Boolean) jsExecutor.executeScript(script, contentElement);
		} else {
			return false;
		}
	}
	
	public void tap() {
		tap(driver.getWebDriver(), contentElement);
	}
	
	public static void tap(WebDriver driver, WebElement element) {
		if (driver instanceof HasTouchScreen) {
			TouchActions tap = new TouchActions(driver).singleTap(element);
			tap.perform();
		} else {
			element.click();
		}
	}
	
	public void longtap() {
		longtap(driver.getWebDriver(), contentElement);
	}
	
	public static void longtap(WebDriver driver, WebElement element) {
		if (driver instanceof HasTouchScreen) {
			TouchActions longtap = new TouchActions(driver);
			Point center = getCenter(element);
			longtap.down(center.getX(), center.getY());
			longtap.perform();
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {}
			longtap.up(center.getX(), center.getY());
			longtap.perform();
		} else {
			Locatable locatable = (Locatable) element;
			Coordinates coords = locatable.getCoordinates();
			Mouse mouse = ((HasInputDevices) driver).getMouse();
			mouse.mouseDown(coords);
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {}
			mouse.mouseUp(coords);
		}
	}
	
	protected static Point getCenter(WebElement element) {
		Dimension size = element.getSize();
		int halfWidth = size.getWidth() / 2;
		int halfHeight = size.getHeight() / 2;

		Point loc = element.getLocation();
		int posX = loc.getX() + halfWidth;
		int posY = loc.getY() + halfHeight;
		
		Point point = new Point(posX, posY);
		return point;
	}
	
	public void track(int x, int y, int step) {
		track(driver.getWebDriver(), contentElement, x, y, step);
	}
	
	public static void track(WebDriver driver, WebElement element, int x, int y, int step) {
		if (driver instanceof HasTouchScreen) {
			if (step == 0) {
				step = 1;
				// TODO: no move if step == 0
			}
			
			Point center = getCenter(element);
			
			int posX = center.getX();
			int posY = center.getY();
			
			int endX = posX + x;
			int endY = posY + y;

			TouchActions touchAction = new TouchActions(driver);
			touchAction.down(posX, posY);
			
			while ((x < 0 && posX > endX || x > 0 && posX < endX) || (y < 0 && posY> endY || y > 0 && posY < endY)) {
				if (x > 0 && posX < endX) {
					if (posX + step > endX) {
						posX += endX - (posX + step);
					} else {
						posX += step;
					}
				}

				else if (x < 0 && posX > endX) {
					if (posX - step < endX) {
						posX -= endX + (posX - step);
					} else {
						posX -= step;
					}
				}

				if (y > 0 && posY < endY) {
					if (posY + step > endY) {
						posY += endY - (posY + step);
					} else {
						posY += step;
					}
				}

				else if (y < 0 && posY > endY) {
					if (posY - step < endY) {
						posY -= endY + (posY - step);
					} else {
						posY -= step;
					}
				}

				touchAction.move(posX, posY);
			}

			touchAction.up(posX, posY)
			.perform();
		}
		else {
			Actions mouseAction = new Actions(driver);
			mouseAction.dragAndDropBy(element, x, y);
		}
	}
	
	public void scrollTo(int x, int y) {
		String script = "qx.ui.mobile.core.Widget.getWidgetById(arguments[0].id).scrollTo(" + x + ", " + y + ")";
		List<WebElement> scrollContainers = driver.findElements(By.cssSelector(".scroll"));
		
		Iterator<WebElement> itr = scrollContainers.iterator();
		while (itr.hasNext()) {
			WebElement scroller = itr.next();
			if (scroller.isDisplayed()) {
				driver.executeScript(script, scroller);
			}
		}
	}

}
