/* ************************************************************************

   qxwebdriver-java

   http://github.com/qooxdoo/qxwebdriver-java

   Copyright:
     2012-2013 1&1 Internet AG, Germany, http://www.1und1.de

   License:
     LGPL: http://www.gnu.org/licenses/lgpl.html
     EPL: http://www.eclipse.org/org/documents/epl-v10.php
     See the license.txt file in the project's top-level directory for details.

   Authors:
     * Daniel Wagner (danielwagner)

************************************************************************ */
package org.oneandone.qxwebdriver;

import java.util.List;

import org.oneandone.qxwebdriver.resources.JavaScript;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public abstract class By extends org.openqa.selenium.By {

	public WebElement findElement(SearchContext context) {
		return null;
	}

	public List<WebElement> findElements(SearchContext context) {
		return null;
	}

	/**
	 * Searches for elements by traversing the qooxdoo application's widget
	 * hierarchy. See the <a href="TODO">qxh locator manual page</a> for details.
	 *
	 * This strategy will ignore any widgets that are not currently visible, as
	 * determined by checking the qooxdoo property <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.core.Widget~isSeeable!method_public">seeable</a>.
	 *
	 * @param locator Locator specification
	 * @return By.ByQxh
	 */
	public static By qxh(final String locator) {
		if (locator == null) {
			throw new IllegalArgumentException(
					"Can't find elements without a locator string.");
		}

		return new ByQxh(locator, true);
	}

	/**
	 * Searches for elements by traversing the qooxdoo application's widget
	 * hierarchy. See the <a href="TODO">qxh locator manual page</a> for details.
	 *
	 * @param locator Locator specification
	 * @param onlySeeable <code>false</code> if invisible widgets should be
	 * traversed. Note that this can considerably increase execution time.
	 * @return configured ByQxh instance
	 */
	public static By qxh(final String locator, final Boolean onlySeeable) {
		if (locator == null) {
			throw new IllegalArgumentException(
					"Can't find elements without a locator string.");
		}
		return new ByQxh(locator, onlySeeable);
	}

	/**
	 * Mechanisms used to locate elements within a qooxdoo Desktop application.
	 *
	 */
	public static class ByQxh extends By {

		private final String locator;
		private Boolean onlySeeable;

		public ByQxh(String locator, Boolean onlySeeable) {
			this.locator = locator;
			this.onlySeeable = onlySeeable;
		}

		public List<WebElement> findElements(SearchContext context) {
			//TODO: findByQxh only returns the first match
			throw new RuntimeException("ByQxh.findElements is not yet implemented.");
		}


		/**
		 * Searches for elements by traversing the qooxdoo application's widget
		 * hierarchy using the current SearchContext as the root node.
		 * See the <a href="TODO">qxh locator manual page</a> for details.
		 */
		public WebElement findElement(SearchContext context) {
			JavascriptExecutor jsExecutor;

			RemoteWebElement contextElement = null;

			if (context instanceof RemoteWebElement) {
				contextElement = (RemoteWebElement) context;
				jsExecutor = (JavascriptExecutor) contextElement.getWrappedDriver();
			}
			else {
				 jsExecutor = (JavascriptExecutor) context;
			}

			String script  = JavaScript.INSTANCE.getValue("qxh");

			try {
				Object result;
				if (contextElement == null) {
					// OperaDriver.executeScript won't accept null as an argument
					result = jsExecutor.executeScript(script, locator, onlySeeable);
				} else {
					try {
						result = jsExecutor.executeScript(script, locator, onlySeeable, (WebElement) contextElement);
					} catch(com.opera.core.systems.scope.exceptions.ScopeException e) {
						// OperaDriver will sometimes throw a ScopeException if executeScript is called
						// with an OperaWebElement as argument
						return null;
					}

				}
				return (WebElement) result;

			} catch(org.openqa.selenium.WebDriverException e) {
				String msg = e.getMessage();
				if (msg.contains("Error resolving qxh path") ||
					// IEDriver doesn't include the original JS exception's message :(
					msg.contains("JavaScript error")) {
					return null;
				}
				else if (msg.contains("Illegal path step")) {
					String reason = "Invalid qxh selector " + locator.toString();
					throw new InvalidSelectorException(reason, e);
				}
				else {
					String reason = "Error while processing selector " + locator.toString();
					throw new org.openqa.selenium.WebDriverException(reason, e);
				}
			}
		}

		public String toString() {
			return "By.qxh: " + locator;
		}
	}
}
