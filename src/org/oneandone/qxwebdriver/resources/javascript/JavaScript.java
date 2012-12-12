package org.oneandone.qxwebdriver.resources.javascript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaScript {

	public static String RESOURCE_PATH = "/org/oneandone/qxwebdriver/resources/javascript/";
	
	public String getScript(String scriptId) throws IOException {
		String path = JavaScript.RESOURCE_PATH + scriptId + ".js";
		InputStream in = this.getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String text = "";
		String line;
		
		while ((line = br.readLine()) != null) {
			text += line;
		}
		br.close();
		
		Pattern pattern = Pattern.compile("function\\(\\)\\{(.*?)\\};$", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			text = matcher.group(1);
		}
		return text;
	}
}
