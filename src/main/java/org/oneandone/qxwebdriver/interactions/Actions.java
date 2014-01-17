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

package org.oneandone.qxwebdriver.interactions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.ButtonReleaseAction;
import org.openqa.selenium.interactions.ClickAndHoldAction;
import org.openqa.selenium.interactions.MoveMouseAction;
import org.openqa.selenium.interactions.MoveToOffsetAction;
import org.openqa.selenium.internal.Locatable;


/**
 * Interactions for qx.Desktop widgets
 */
public class Actions extends org.openqa.selenium.interactions.Actions {

	public Actions(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Drag and drop action with additional mouse move required by qooxdoo.  
	 */
	public Actions dragAndDrop(WebElement source, WebElement target) {
		action.addAction(new ClickAndHoldAction(mouse, (Locatable) source));
		// qx needs an additional mousemove event to initialize a drag session
		action.addAction(new MoveToOffsetAction(mouse, null, 5, 5));
		action.addAction(new MoveMouseAction(mouse, (Locatable) target));
		action.addAction(new ButtonReleaseAction(mouse, (Locatable) target));
		return this;
	}

}
