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

package org.oneandone.qxwebdriver.resources;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;

public class JavaScriptRunner {

	public JavaScriptRunner(JavascriptExecutor jsExecutor) {
		exec = jsExecutor;
		exec.executeScript(namespace + " = {};");
	}

	protected JavascriptExecutor exec;

	static String namespace = "qxwebdriver";

	List<String> createdFunctions = new ArrayList<String>();

	public Object runScript(String scriptId, Object... args) {
		if (!createdFunctions.contains(scriptId)) {
			defineFunction(scriptId);
		}

		String fqFunctionName = namespace + "." + scriptId;
		String call = "return " + fqFunctionName + "(arguments);";
		return exec.executeScript(call, args);
	}

	protected void defineFunction(String scriptId) {
		String fqFunctionName = namespace + "." + scriptId;
		String function = "function(arguments) {" +  JavaScript.INSTANCE.getValue(scriptId) + "}";
		String script = fqFunctionName + " = " + function;
		exec.executeScript(script);
		createdFunctions.add(scriptId);
	}

}
