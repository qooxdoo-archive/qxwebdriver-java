package org.oneandone.qxwebdriver.resources.javascript;

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
	
	protected String packageName = this.getClass().getPackage().getName();
	protected String fileExtension = ".js";
	
	public String getValue(String resourceId) {
		if (!resources.containsKey(resourceId)) {
			String resource = readResource(getResourcePath(resourceId));
			resource = manipulateResource(resource);
			resources.put(resourceId, resource);
		}
		
		return resources.get(resourceId);
	}
	
	protected String getResourcePath(String resourceId) {
		resourceId = packageName + "." + resourceId;
		resourceId = "/" + resourceId.replace(".", "/") + fileExtension;
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