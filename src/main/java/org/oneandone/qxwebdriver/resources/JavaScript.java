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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum JavaScript {
	INSTANCE;
	HashMap<String, String> resources = new HashMap<String, String>();
	protected String suffix = "-min";
	protected String fileExtension = ".js";

	public String getValue(String resourceId) {
		if (!resources.containsKey(resourceId)) {
			String resourcePath = getResourcePath(resourceId);
			addResourceFromPath(resourceId, resourcePath);
		}

		return resources.get(resourceId);
	}
	
	public void addResource(String resourceId, String resourcePath) {
		if (!resources.containsKey(resourceId)) {
			addResourceFromPath(resourceId, resourcePath);
		}
	}
	
	protected void addResourceFromPath(String resourceId, String resourcePath) {
		String resource = readResource(resourcePath);
		resource = manipulateResource(resource);
		resources.put(resourceId, resource);
	}

	protected String getResourcePath(String resourceId) {
		resourceId = "javascript." + resourceId;
		resourceId = "/" + resourceId.replace(".", "/") + suffix + fileExtension;
		return resourceId;
	}

	protected String readResource(String resourcePath) {
		InputStream in = this.getClass().getResourceAsStream(resourcePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String text = "";
		String line;

		try {
			while ((line = br.readLine()) != null) {
				text += line;
			}
			br.close();
		} catch (IOException e) {
			throw new RuntimeException("Couldn't read resource file.", e);
		}

		return text;
	}

	protected String manipulateResource(String resource) {
		Pattern pattern = Pattern.compile("function\\(\\)\\{(.*?)\\};$", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(resource);
		if (matcher.find()) {
			resource = matcher.group(1);
		}

		return resource;
	}
}
