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

package org.oneandone.qxwebdriver.ui;

public interface Touchable extends Widget {
	
	/**
	 * Performs a single tap on this widget
	 */
	public void tap();
	
	
	/**
	 * Performs a long tap on this widget
	 */
	public void longtap();
	
	
	/**
	 * Drags this widget by the given offsets
	 * @param x Amount of pixels to move horizontally
	 * @param y Amount of pixels to move vertically
	 * @param step Generate a move event every (step) pixels
	 */
	public void track(int x, int y, int step);
}
