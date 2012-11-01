package org.zadatak.zadatak;
import java.util.Calendar;

/**
 * 
 */

/**
 * @author nickt_000
 *
 */
public class CalendarEvent implements Comparable<CalendarEvent> {

	String name;
	Calendar startDate;
	Calendar endDate;
	
	
	public CalendarEvent(String c_name, Calendar c_startDate, Calendar c_endDate){
		name = c_name;
		startDate = c_startDate;
		endDate = c_endDate;
	}


	public int compareTo(CalendarEvent another) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//
//	protected int progress;
//	protected UUID eventId;
//	
//	//EventLength is defined in days
//	protected int eventLength;
//	protected int hoursToComplete;
//	protected String eventName;
//	
//	/**
//	 * Empty Constructor. Should not be used!
//	 */
//	public CalendarEvent(){};
//	
//	/**
//	 * Default constructor for events with default parameters
//	 * @param eventName
//	 */
//	public CalendarEvent(String eventName){
//		if(eventName.equalsIgnoreCase("busy") || eventName.equalsIgnoreCase("null")){
//			this.progress = 0;
//			this.eventLength = 0;
//			this.eventId = UUID.randomUUID();
//			this.eventName = eventName;
//			this.hoursToComplete = 0;
//		} else {
//			this.progress = 0;
//			this.eventLength = 5;
//			this.eventId = UUID.randomUUID();
//			this.eventName = eventName;
//			this.hoursToComplete = 5;
//		}
//	}
//	/**
//	 * Constructor with each parameter defined
//	 * @param name
//	 * @param length
//	 * @param hoursToComplete
//	 * @param progress
//	 */
//	public CalendarEvent( String name, int length,int hoursToComplete, int progress){
//		this.progress = progress;
//		this.eventLength = length;
//		this.hoursToComplete = hoursToComplete;
//		this.eventId = UUID.randomUUID();
//		this.eventName = name;
//	}
//		
//	/* Setters and Getters */
//	
//	public int getProgress(){
//		return progress;
//	}
//	
//	public String getEventId(){
//		return eventId.toString();
//	}
//	
//	public int getEventLength(){
//		return eventLength;
//	}
//	
//	public String getName(){
//		return eventName;
//	}
//	
//	public int getHoursToComplete(){
//		return hoursToComplete;
//	}
//	
//	public void setProgress(int newProgress){
//		this.progress = newProgress;
//	}
//	
//	public void resetEventLength(int newLength){
//		this.eventLength = newLength;
//	}
//	
//	public String toString(){
//		return getName();
//	}
//	
//	public int compareTo(CalendarEvent e){
//		return this.getName().compareTo(e.getName());
//	}
//	
//	public boolean equals(CalendarEvent e){
//		return this.getName().equalsIgnoreCase(e.getName());
//	}
//	public boolean equals(String string){
//		return this.getName().equalsIgnoreCase(string);
//				
//	}
//	public void updateRemainingTime(int newTime){
//		hoursToComplete = newTime;
//	}
	

}
