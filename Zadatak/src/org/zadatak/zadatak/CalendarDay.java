package org.zadatak.zadatak;
import java.util.Calendar;
import java.util.List;


public class CalendarDay {
	//This class needs to take in a Calendar Object, translate it into an array of free and busy times.
	//Out of this day, the class needs to be able find the next free hour to schedule and add it to the user's google calendar
	
	Calendar day;
	Task task;
	String[] hours = new String[24];
	ZadatakApp app;
	
	public CalendarDay(Calendar c_Day, Task c_task, ZadatakApp c_app){
		day = c_Day;
		task = c_task;
		app = c_app;
		addBusyTimes(0,8);         //Times to sleep! 
		addBusyTimes(22,24);      
	}
	
	
	public void pushToCalendar(){
		BuildEventTable bet = new BuildEventTable();
		List<CalendarEvent> eventsThisDay = bet.getEventsThisDay(app,day);
		
		for(CalendarEvent e: eventsThisDay)
			for(int i = e.startDate.get(Calendar.HOUR_OF_DAY); i < e.endDate.get(Calendar.HOUR_OF_DAY); i++)
				hours[i] = e.name;
		
		int freehour = 0;
		for(int i = 0; i < 24; i++)
			if(hours[i] == null)
				freehour = i;		
		
		bet.addEvent(day, freehour, task.get(Task.Attributes.Name));
	}
	
	private void addBusyTimes(int starthour, int endhour){
		for(int i = starthour; i < endhour; i++)
			hours[i] = "Busy";
	}	
}