package org.zadatak.zadatak;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

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
	
	public void extractBundle(Bundle bundle){
		Attributes[] attributes = Task.Attributes.values();
		for (int i = 0 ; i < attributes.length; i++) {
			if (bundle.containsKey(attributes[i].toString())){
				set(attributes[i],bundle.getString(attributes[i].toString()));
			}
		}
	}
	public void packIntent(Intent instance) {
		Attributes[] attributes = Task.Attributes.values();
		for (int i = 0 ; i < attributes.length; i++) {
			instance.putExtra(attributes[i].toString(), get(attributes[i]));
		}
	}
	
	
	// Now for some getters and setters that are used with the UI elements
	/*
	 * Set from edittext
	 */
	public void set(Attributes attribute, EditText text) {
		set(attribute,text.getText().toString());
	}
	public void get(Attributes attribute, EditText text) {
		String savedText = get(attribute);
		text.setText(savedText);
	}
	// DATETIME
	public void set(Attributes attribute, DatePicker datePicker) {
		String date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
		set(attribute, date);
	}
	public void get(Attributes attribute, DatePicker datePicker) {
		String alldates = get(attribute);
		String[] dates = alldates.split("/");
		int year = Integer.parseInt(dates[2]);
		int month = Integer.parseInt(dates[1]);
		int day = Integer.parseInt(dates[0]);
		datePicker.init(year, month, day, null);
	}
	// Checkbox
	public void set (Attributes attribute, CheckBox checkBox){
		set(attribute,checkBox.isChecked() ? "TRUE" : "FALSE");
	}
	public void get (Attributes attribute, CheckBox checkBox){
		boolean checked = get(attribute).equals("TRUE");
		checkBox.setChecked(checked);
	}
	// Slider Bar
}
