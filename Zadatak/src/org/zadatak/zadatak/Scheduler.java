package org.zadatak.zadatak;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TimeZone;

import android.app.Activity;

/**
 * @author Nick Timakondu
 * Date Last Edited: 10/17/2012
 * 
 * This file contains the scheduling algorithm and holds the event queue
 */
public class Scheduler extends Activity {
	
	class FreeHours implements Comparable<FreeHours> {
		Calendar Day;
		int hoursfree;
		
		public FreeHours(Calendar n_Day){
			Day = n_Day;
		}
		public FreeHours(Calendar n_Day, int n_freeHours){
			Day = n_Day;
			hoursfree = n_freeHours;
		}
		
		//Getters and Setters
		public void setFreeHours(int n_freeHours){
			hoursfree = n_freeHours;
		}
		
		public int getFreeHours(){
			return hoursfree;
		}
		
		public Calendar getDay(){
			return Day;
		}
		public int compareTo(FreeHours another) {
			Integer i1 = Integer.valueOf(hoursfree);
			Integer i2 = Integer.valueOf(another.getFreeHours());
			return i1.compareTo(i2);
		}
	}
	
	List<FreeHours> listOfDays = new LinkedList<FreeHours>();
	private PriorityQueue<Task> queue;
	
	static Comparator<Task> DATE_ORDER = new Comparator<Task>(){
		//Compare by due date then by priority
		public int compare(Task t1, Task t2) {
			// TODO Auto-generated method stub
			Calendar c1, c2;
			c1 = c2 = Calendar.getInstance((TimeZone.getTimeZone("UTC")));
			SimpleDateFormat df = new SimpleDateFormat("MM/DD/YYYY");
			try {
				c1.setTime(df.parse(t1.get(Task.Attributes.Duedate)));
				c2.setTime(df.parse(t2.get(Task.Attributes.Duedate)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Due date is equal
			if(c1.compareTo(c2) == 0){
				Integer t1priority = Integer.parseInt(t1.get(Task.Attributes.Priority));
				Integer t2priority = Integer.parseInt(t2.get(Task.Attributes.Priority));
			    return t1priority.compareTo(t2priority);
				
			} else 
				return c1.compareTo(c2);
		}
		
	};
	
	/**
	 * Build Event Queue from database task list
	 */
	public Scheduler(){
		ZadatakApp app = (ZadatakApp) getApplicationContext();
		List<Task> tasks = app.dbman.getAllTasks(); 
		queue.addAll(tasks);
	}
	
	/**
	 * 
	 */
	public void getFreeHours(){
		BuildEventTable bet = new BuildEventTable();  //Initialize class to get busy times
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		int days = cal.getActualMaximum(Calendar.MONTH); //Forecasting one month in advance
		int hoursFree;
		for(int i = 0; i < days; i++){
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + i); //Advance day by one
			hoursFree = bet.hoursFreethisDay(cal); //Get free hours this day
			FreeHours fh = new FreeHours(cal,hoursFree); //Assign free hours to this day
			listOfDays.add(fh);
		}
	}
	
	/**
	 * Schedule all the events in the queue by adding one hour at a time
	 * to days that have the most free days available. Continue until all
	 * events have been scheduled. 
	 */
	public void scheduleEvents(){
		Calendar nextOpenDay;
		int maxDayHourRemaining = 0;
		
		//Continue Algorithm until all events have been scheduled. 
		// Add priority so that higher priority events get scheduled first
		while(!queue.isEmpty()){
			Task currTask = queue.remove();
			int hrsRemaining = Integer.parseInt(currTask.get(Task.Attributes.Hours)); //Get remaining hours for this task
			while(hrsRemaining > 0){
				SimpleDateFormat df = new SimpleDateFormat("MM/DD/YYYY");
				Calendar dueDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				try {
					dueDate.setTime(df.parse(currTask.get(Task.Attributes.Duedate)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//If event has a deadline, find the day with the most open spots 
				//given that deadline
				nextOpenDay = maxDayHourRemaining(dueDate);
				CalendarDay calDay = new CalendarDay(nextOpenDay,currTask);
				calDay.pushToCalendar();
				currTask.set(Task.Attributes.Hours, Integer.toString(Integer.parseInt(currTask.get(Task.Attributes.Hours) + 1)));
				
			}
		}
	}
	
	
	/**
	 * Returns the day with the most free hours remaining
	 * between now and the end of time (end of calendar)
	 * @return day index with most free hours remaining
	 */
	private Calendar maxDayHourRemaining(){
		Collections.sort(listOfDays);
		return listOfDays.get(listOfDays.size()-1).getDay();
	}
	/**
	 * Returns the day with the most free hours remaining between 
	 * now and endDay
	 * @param endDay The upper limit to look for free time
	 * @return day index with the most free hours remaining 
	 * 		   given these constraints
	 */
	private Calendar maxDayHourRemaining(Calendar endDay){
		Collections.sort(listOfDays);
		for(int i = listOfDays.size()-1; i > 0;i--){
			if(listOfDays.get(i).getDay().before(endDay)){
				return listOfDays.get(i).getDay();
			}
		}
		return null;
	}
}
