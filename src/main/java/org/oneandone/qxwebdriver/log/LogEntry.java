package org.oneandone.qxwebdriver.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LogEntry {

	public LogEntry(String json) {
		JSONParser parser = new JSONParser();
		
		Object obj;
		try {
			obj = parser.parse(json);
			JSONObject jsonEntry = (JSONObject) obj;
			clazz = (String) jsonEntry.get("clazz");
			level = (String) jsonEntry.get("level");
			time = (String) jsonEntry.get("time");
			
			JSONArray jsonItems = (JSONArray) jsonEntry.get("items");
			Iterator<String> itr = jsonItems.iterator();
			while (itr.hasNext()) {
				items.add(itr.next());
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String clazz;
	public String level;
	public List<String> items = new ArrayList<String>();
	public String time;
	
	public String toString() {
		if (clazz == null) {
			return time + " " + level + ": " + items.toString();
		} else {
			return time + " " + level + ": " + clazz + " " + items.toString();
		}
	}
}
