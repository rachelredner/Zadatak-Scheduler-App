package org.zadatak.zadatak;

import java.util.HashMap;
import java.util.Map;

public class Task {
	
	enum Attributes {
		Name,
		Duedate,
		Hours,
		Priority
	}
	
	Map<Attributes,String> values = new HashMap<Attributes,String>();
	
	public void set(Attributes attribute,String value) {values.put(attribute, value);}
	public String get (Attributes attribute) {return values.get(attribute);}
}
