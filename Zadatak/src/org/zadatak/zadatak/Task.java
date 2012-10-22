package org.zadatak.zadatak;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;

public class Task {
	
	// An enumeration of all of the possible elements of the task
	enum Attributes {
		Name,
		Duedate,
		Hours,
		Progress,
		Priority
	}
	
	// The actual data structure that stores the values
	Map<Attributes,String> values = new HashMap<Attributes,String>();
	
	// An ID of where the task is in the database, does not always have to be set
	long id;
	
	/*************************** BASIC GETTER AND SETTER **************************\
	| These are the basic getters and setters. They will save and load different   |
	| pieces of your task based on the enum Attributes                             |
	\******************************************************************************/
	public void set(Attributes attribute,String value) {values.put(attribute, value);}
	public String get (Attributes attribute) {return values.get(attribute);}
	
	/******************************* EXTRACT BUNDLE *******************************\
	| This function takes in a bundle and extracts the task object that was        |
	| packed into it using the Pack Intent function. This is used for passing      |
	| single tasks between events, for example the edit task activity.             |
	\******************************************************************************/
	public void extractBundle(Bundle bundle){
		Attributes[] attributes = Task.Attributes.values();
		for (int i = 0 ; i < attributes.length; i++) {
			if (bundle.containsKey(attributes[i].toString())){
				set(attributes[i],bundle.getString(attributes[i].toString()));
			}
		}
	}
	
	/********************************* PACK INTENT ********************************\
	| This function packs the task into an intent to be sent to a new activity.    |
	| Once in the new activity the extractBundle() function can be used to         |
	| extract the task back into a task object                                     |
	\******************************************************************************/
	public void packIntent(Intent instance) {
		Attributes[] attributes = Task.Attributes.values();
		for (int i = 0 ; i < attributes.length; i++) {
			instance.putExtra(attributes[i].toString(), get(attributes[i]));
		}
	}
	
	  //////////////////////////////////////////////////////////////////////////////
	 ////////////////////////// MISC GETTERS AND SETTERS ////////////////////////// 
	//////////////////////////////////////////////////////////////////////////////  
	
	/******************************** SET EDIT TEXT *******************************\
	| The set edit text function sets the given attribute equal to the value of    |
	| the text contained within the specified edit text box. You can use the       |
	| paired get() function to set the edit text back to it's value                |
	\******************************************************************************/
	public void set(Attributes attribute, EditText text) {
		set(attribute,text.getText().toString());
	}
	
	/******************************** GET EDIT TEXT *******************************\
	| This function will set an edit text equal to the attribute of the task       |
	| specified. use the paired set() function to set the task's attribute equal   |
	| to the edit text's value                                                     |
	\******************************************************************************/
	public void get(Attributes attribute, EditText text) {
		String savedText = get(attribute);
		text.setText(savedText);
	}
	
	/******************************* SET DATE PICKER ******************************\
	| Extract the year, month, and day from the date picker function and save it   |
	| to the task. This date is formated Day/Month/Year, if it is changed then     |
	| the paired function get date picker must also be changed                     |
	\******************************************************************************/
	public void set(Attributes attribute, DatePicker datePicker) {
		String date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
		set(attribute, date);
	}
	
	/******************************* GET DATE PICKER ******************************\
	| This function takes the data found by the specific attribute and fills it    |
	| into the given datapicker variable. If the data is not formatted correctly   |
	| then the function will fail. The set date picker function should put the     |
	| date picker's data into the task with the correct formatting                 |
	\******************************************************************************/
	public void get(Attributes attribute, DatePicker datePicker) {
		String alldates = get(attribute);
		String[] dates = alldates.split("/");
		int year = Integer.parseInt(dates[2]);
		int month = Integer.parseInt(dates[1]);
		int day = Integer.parseInt(dates[0]);
		datePicker.init(year, month, day, null);
	}
	
	/******************************** SET CHECKBOX ********************************\
	| This function sets the attribute equal to the value of the checkbox, either  |
	| checked or unchecked.                                                        |
	\******************************************************************************/
	public void set (Attributes attribute, CheckBox checkBox){
		set(attribute,checkBox.isChecked() ? "TRUE" : "FALSE");
	}
	
	/******************************** GET CHECKBOX ********************************\
	| This function gets the attribute requested and sets the checkbox equal to    |
	| it's value, either checked or unchecked                                      |
	\******************************************************************************/
	public void get (Attributes attribute, CheckBox checkBox){
		boolean checked = get(attribute).equals("TRUE");
		checkBox.setChecked(checked);
	}
	
	/******************************** SET SEEK BAR ********************************\
	| This function sets the attribute equal to the progress variable of the seek  |
	| bar provided                                                                 |
	\******************************************************************************/
	public void set (Attributes attribute, SeekBar seekBar) {
		set(attribute,""+seekBar.getProgress());
	}
	
	/******************************** GET SEEK BAR ********************************\
	| This function will set the progress of the seek bar given equal to the       |
	| integer stored inside of the task object                                     |
	\******************************************************************************/
	public void get(Attributes attribute, SeekBar seekBar) {
		String progress = get(attribute);
		seekBar.setProgress(Integer.parseInt(progress));
	}
}
