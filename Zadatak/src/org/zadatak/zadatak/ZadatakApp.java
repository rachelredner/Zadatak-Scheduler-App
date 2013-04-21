package org.zadatak.zadatak;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.app.Application;
import android.util.Log;
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
	
	// a function that returns the date of today as an integer
	public int today() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_YEAR) + (365*calendar.get(Calendar.YEAR));
	}
	
	public void schedule() {
		int daysToSchedule = Integer.parseInt(setting.get(Settings.DaysToSchedule));
		int currentDay = today();
		
		// Get the list of tasks
		List<Task> tasks = dbman.getAllTasks();
		
		// Dont try to schedule no tasks
		if (tasks.isEmpty()) {
			return;
		}

		// Generate the busy time for the days to be scheduled
		List<Map<Integer,Integer>> busyTime = new ArrayList<Map<Integer,Integer>>();
		
		int startTime = dbman.getSetting(Settings.StartTime); // Hard Coded for now but will be taken from the settings page
		int endTime = dbman.getSetting(Settings.EndTime);
		
		for (int i = 0; i < daysToSchedule; i++) {
			Map<Integer,Integer> busyTimeToday = new HashMap<Integer,Integer>();
			busyTimeToday.put(0, startTime);
			busyTimeToday.put(endTime,1440);
			busyTime.add(busyTimeToday);
		}
		
		
		roundRobinSchedule(daysToSchedule, currentDay, tasks, busyTime);
	}
	
	// This is the function to naively schedule events in a round robin style until
	// the day is completely filled.
	public void roundRobinSchedule (int daysToSchedule, int currentDay, List<Task> tasks, List<Map<Integer,Integer>> busyTimes) {
		//toaster(""+currentDay);
		for (int i = 0; i < daysToSchedule; i++) {
			Queue<Task> roundRobinQueue = new LinkedList<Task>();
			// Fill the queue with the tasks for the day
			for (Task task : tasks) { roundRobinQueue.add(task); }
			
			
			// Get the busy times for the day
			Map<Integer,Integer> busyTime = busyTimes.get(i);
			
			//Create the zadatak schedule for the day
			Set<TaskBlock> scheduledTasks = new HashSet<TaskBlock>();
			
			// Go through and schedule the day
			Task currentTask = null;
			int currentTaskStartTime = 0;
			for (int time = 0; time < 1440; time++) {
				// If you hit a busy time block
				if (busyTime.containsKey(time)) {
					// If a task is currently being scheduled
					if (currentTask != null) {
						// End the task and schedule it
						TaskBlock taskBlock = new TaskBlock(currentTaskStartTime, time, currentTask);
						scheduledTasks.add(taskBlock);
						currentTask = null;
					}
					
					// Skip to the end of the busy time
					time = busyTime.get(time);
				}
				
				// If a task is scheduled for more then 30 minutes start a new one
				if (time - currentTaskStartTime >= 30 && currentTask != null) {
					TaskBlock taskBlock = new TaskBlock(currentTaskStartTime, time, currentTask);
					scheduledTasks.add(taskBlock);
					currentTask = null;
				}
				
				// Begin Scheduling a new task
				if (currentTask == null) {
					currentTask = roundRobinQueue.remove();
					roundRobinQueue.add(currentTask);//re append the task to the end of the list
					currentTaskStartTime = time;
				}
			}
			
			// Save the schechuled data in the databse
			dbman.setTasks(i+currentDay, scheduledTasks);
			Log.v("calendar", "Set Tasks for day " + (i+currentDay));
		}
	}
	
	public void postpone(int index) {
		toaster("Unimplemented");
	}
	
	/**
	 * Allows sorting through Tasks by due date then by priority
	 */
	static Comparator<Task> DATE_ORDER = new Comparator<Task>() {
		public int compare(Task t1, Task t2) {
			Calendar c1, c2;
			c1 = c2 = Calendar.getInstance((TimeZone.getTimeZone("UTC")));
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy",
					Locale.ENGLISH);
			try {
				c1.setTime(df.parse(t1.get(Task.Attributes.Duedate)));
				c2.setTime(df.parse(t2.get(Task.Attributes.Duedate)));
				
			} catch (java.text.ParseException e) {
				Log.e("ParseException", e.getMessage());
				e.printStackTrace();
			}
			// Due date is equal
			if (c1.compareTo(c2) == 0) {
				Integer t1priority = t1.get(Task.Attributes.Priority)=="TRUE"?1:0;
				Integer t2priority = t2.get(Task.Attributes.Priority)=="TRUE"?1:0;
				return t1priority.compareTo(t2priority);

			} else
				return c1.compareTo(c2);
		}

	};
	
	public void nonGreedySchedule(int daysToSchedule, int currentDay, List<Task> tasks, List<Map<Integer, Integer>> busyTime){
		
		// Organize the list of tasks by priority, then by due date
		Queue<Task> sortedTasks = new ConcurrentLinkedQueue<Task>();
		List<Task> listCopy = new LinkedList<Task>(tasks);
		
		Collections.sort(listCopy, DATE_ORDER);
		
		// Put tasks into queue
		for(Task t: listCopy)
			sortedTasks.add(t);
		
		while(!sortedTasks.isEmpty()){
			//Get Task at the top of the queue
			Task currentTask = sortedTasks.poll();
			
			//Queue is empty
			if(currentTask == null)
				continue;
			
			// For the next daystoSchedule, find the day with the most amount of free time
			int day = nextFreeDayProcrastinate(daysToSchedule, busyTime);
			// Place an hour block of this task into this day
			
			
		}
		
	}
	
	/**
	 * Given the number of days to Schedule, return the furthest day with the most free time
	 * @param daysToSchedule number of days from today to schedule
	 * @param busyTime list of busy times per day
	 * @return furthest day with most free time
	 */
	private int nextFreeDayProcrastinate(int daysToSchedule, List<Map<Integer, Integer>> busyTime){
		int[] freetimes = new int[daysToSchedule];
		
		//Get free times for each day
		for(int i = 0; i < daysToSchedule; i++){
			Map<Integer, Integer> currentDay = busyTime.get(i);
			int freeTime = 1440;
			
			for(Integer startTime : currentDay.keySet()){
				freeTime -= (currentDay.get(startTime) - startTime);
			}
			
			freetimes[i] = freeTime;
		}
		
		int max = freetimes[0];
		int maxElem = 0;
		for(int j = freetimes.length -1 ; j > -1; j--){
			if(freetimes[j] > max)
				maxElem = j;
		}
		
		return maxElem;
	}
	
	/**
	 * Find the next hour of free time on this day
	 * @param startingHour hour to start looking at
	 * @param busyDay this day's busy times
	 * @return integer with the next free hour
	 */
	private int nextFreeHour(int startingHour, Map<Integer, Integer> busyDay){
		Set<Integer> keys = busyDay.keySet();
		int start = 0;
		Iterator<Integer> keyIterator = keys.iterator();
		while(keyIterator.hasNext()){
			int key = keyIterator.next();
			if(key <= start){
				if(busyDay.get(key) > 60){
					
				}
					//Do Stuff
			}
			// Not too sure where I'm going with this
		}
		return -1;
	}
	
	
}
enum Settings {
	StartTime,
	EndTime,
	DaysToSchedule,
	UseSchedule,
	SchedulingSchema
	
}
