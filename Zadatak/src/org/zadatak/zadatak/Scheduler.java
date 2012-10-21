package org.zadatak.zadatak;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Nick Timakondu
 * Date Last Edited: 10/17/2012
 * 
 * This file contains the scheduling algorithm and holds the eventqueue
 */
public class Scheduler {
	
	private CalendarDay[] cal;
	private ConcurrentLinkedQueue<CalendarEvent> queue;
	
	public Scheduler(){
		initQueue();
		cal = new CalendarDay[7];
		for(int i = 0; i < cal.length; i++){
			cal[i] = new CalendarDay();
		}
	}
	/**
	 * Schedule all the events in the queue by adding one hour at a time
	 * to days that have the most free days available. Continue until all
	 * events have been scheduled. 
	 */
	public void scheduleEvents(){
		CalendarDay currDay = null;
		int maxDayHourRemaining = 0;
		
		//Continue Algorithm until all events have been scheduled. 
		//TODO: Add priority so that higher priority events get scheduled first
		while(!queue.isEmpty()){
			CalendarEvent event = queue.remove();
			int hrsRemaining = event.getHoursToComplete();
			while(hrsRemaining > 0){
				//If event has a deadline, find the day with the most open spots 
				//given that deadline
				if(event.getEventLength() == -1)
					maxDayHourRemaining = maxDayHourRemaining();
				else
					maxDayHourRemaining = maxDayHourRemaining(event.getEventLength());;
				
				currDay = cal[maxDayHourRemaining];		
				int index = currDay.getNextFreeSpace();
				if(index == -1){
					
				}
				currDay.addEvent(index, event);
				hrsRemaining--;
				event.updateRemainingTime(hrsRemaining);				
			}
		}
	}
	
	/**
	 * Get the calendar Array
	 * @return calendar Array
	 */
	public CalendarDay[] getCal(){
		return cal;
	}
	
	/**
	 * Mark a certain period of time as busy
	 * @param day 
	 * @param startHour
	 * @param endHour
	 */
	public void addBusyTimes(int day, int startHour, int endHour){
		CalendarDay currDay = cal[day];
		currDay.addBusyTimes(startHour, endHour);		
	}
	/**
	 * Initialize the event Queue
	 */
	public void initQueue(){
		queue = new ConcurrentLinkedQueue<CalendarEvent>();
	}
	/**
	 * Adds events to the event Queue
	 * @param event to add to end of Queue
	 */
	public void addToQueue(CalendarEvent event){
		queue.add(event);
	}
	
	/**
	 * Returns the day with the most free hours remaining
	 * between now and the end of time (end of calendar)
	 * @return day index with most free hours remaining
	 */
	private int maxDayHourRemaining(){
		int day = 0;
		int max = cal[day].getFreeSpaceThisDay();		
		for(int i = 1; i < cal.length; i++){
			if(cal[i].getFreeSpaceThisDay() > max){
				max = cal[i].getFreeSpaceThisDay();
				day = i;
			}
		}
		return day;
	}
	/**
	 * Returns the day with the most free hours remaining between 
	 * now and endDay
	 * @param endDay The upper limit to look for free time
	 * @return day index with the most free hours remaining 
	 * 		   given these constraints
	 */
	private int maxDayHourRemaining(int endDay){
		int day = 0;
		int max = cal[day].getFreeSpaceThisDay();
		for(int i = 1; i < endDay; i++){
			if(cal[i].getFreeSpaceThisDay() > max ){
				max = cal[i].getFreeSpaceThisDay();
				day = i;
			}
		}
		return day;
	}
}
