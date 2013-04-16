package org.zadatak.zadatak;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.zadatak.zadatak.Task.Attributes;

import android.app.Application;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class ZadatakApp extends Application {
	public String state;
	public DatabaseManager dbman = new DatabaseManager(this);
	
	public void toaster(String string) {
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(this, string, duration);
		toast.show();
	}
	
	
	
	Setting setting = new Setting();
	public class Setting {
		
		public void set(Settings setting,String value) {
			dbman.setSetting(setting.toString(), value);
		}
		public String get (Settings setting) {
			return dbman.getSetting(setting.toString());
		}
		
		///////////// Save checkbox function ported from Task.java
		// TODO change comment to match 'Setting' instead of 'Task'
		/******************************** SET CHECKBOX ********************************\
		| This function sets the attribute equal to the value of the checkbox, either  |
		| checked or unchecked.                                                        |
		\******************************************************************************/
		public void saveCheckbox (Settings setting, CheckBox checkBox){
			
			set(setting,checkBox.isChecked() ? "TRUE" : "FALSE");
		}
		
		/******************************** GET CHECKBOX ********************************\
		| This function gets the attribute requested and sets the checkbox equal to    |
		| it's value, either checked or unchecked                                      |
		\******************************************************************************/
		public void loadCheckbox (Settings setting, CheckBox checkBox){
			String fromString = get(setting);
			if (fromString == null) return;
			boolean checked = fromString.equals("TRUE");
			checkBox.setChecked(checked);
		}
		
		
		public void saveTimePicker (Settings setting, TimePicker timePicker) {
			String toString = "";
			Integer currentHour = timePicker.getCurrentHour();
			Integer currentMinute = timePicker.getCurrentMinute();
			toString = currentHour + ":" + currentMinute;
			set (setting, toString);
		}
		
		public void loadTimePicker (Settings setting, TimePicker timePicker) {
			String fromString = get(setting);
			if (fromString == null) return;
			String[] split = fromString.split(":");
			Integer currentHour = Integer.parseInt(split[0]);
			Integer currentMinute = Integer.parseInt(split[1]);
			timePicker.setCurrentHour(currentHour);
			timePicker.setCurrentMinute(currentMinute);
		}
		
		public void saveRadioGroup(Settings setting, RadioGroup radioGroup) {
			int radioButton = radioGroup.getCheckedRadioButtonId();
			String toString = "" + radioButton;
			set(setting,toString);
		}
		
		public void loadRadioGroup(Settings setting, RadioGroup radioGroup) {
			String fromString = get (setting);
			if (fromString == null) return;
			int radioButtonId = Integer.parseInt(fromString);
			radioGroup.check(radioButtonId);
		}
		  //////////////////////////////////////////////////////////////////////////////
		 ////////////////////////// MISC GETTERS AND SETTERS ////////////////////////// 
		//////////////////////////////////////////////////////////////////////////////  
		
		/******************************** SET EDIT TEXT *******************************\
		| The set edit text function sets the given attribute equal to the value of    |
		| the text contained within the specified edit text box. You can use the       |
		| paired get() function to set the edit text back to it's value                |
		\******************************************************************************/
		public void saveEditText(Settings setting, EditText text) {
			set(setting,text.getText().toString());
		}
		
		/******************************** GET EDIT TEXT *******************************\
		| This function will set an edit text equal to the attribute of the task       |
		| specified. use the paired set() function to set the task's attribute equal   |
		| to the edit text's value                                                     |
		\******************************************************************************/
		public void loadEditText(Settings setting, EditText text) {
			String fromString = get(setting);
			if (fromString == null) return;
			text.setText(fromString);
		}
	}
	
	public void schedule() {
		int daysToSchedule = Integer.parseInt(setting.get(Settings.DaysToSchedule));
		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DAY_OF_YEAR) + (365*calendar.get(Calendar.YEAR));
		
		// Get the list of tasks
		List<Task> tasks = dbman.getAllTasks();
		
		// Generate the busy time for the days to be scheduled
		List<Map<Integer,Integer>> busyTime = new ArrayList<Map<Integer,Integer>>();
		
		int startTime = 480; // Hard Coded for now but will be taken from the settings page
		int endTime = 1410;
		
		for (int i = 0; i < daysToSchedule; i++) {
			Map<Integer,Integer> busyTimeToday = new HashMap<Integer,Integer>();
			busyTimeToday.put(0, startTime);
			busyTimeToday.put(endTime,1440);
			busyTime.add(busyTimeToday);
		}
		
		
		roundRobinSchedule(daysToSchedule, currentDay, tasks, busyTime);
	}
	
	public void roundRobinSchedule (int daysToSchedule, int currentDay, List<Task> tasks, List<Map<Integer,Integer>> busyTime) {
		//toaster(""+currentDay);
		for (int i = 0; i < daysToSchedule; i++) {
			Queue<Task> roundRobinQueue = new LinkedList<Task>();
			// Fill the queue with the tasks for the day
			for (Task task : tasks) { roundRobinQueue.add(task); }
			
			// Go through and schedule the day
			boolean currentlyBusy = false;
			Task currentTask = null;
			int currentTaskStartTime = 0;
			for (int time = 0; time < 1440; time++) {
				if (currentlyBusy){
					// If there is a task currently being scheduled
					if (currentTask != null) {
						
					}
					// look for end times
				}
				else {
					// look for start times
				}
			}
		}
	}
	
	public void postpone(int index) {
		toaster("Unimplemented");
	}
	
	
}
enum Settings {
	StartTime,
	EndTime,
	DaysToSchedule,
	UseSchedule,
	SchedulingSchema
	
}
