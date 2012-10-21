package org.zadatak.zadatak;
import java.util.UUID;

/**
 * 
 */

/**
 * @author nickt_000
 *
 */
public class CalendarEvent implements Comparable<CalendarEvent> {

	private int progress;
	private UUID eventId;
	
	//EventLength is defined in days
	private int eventLength;
	private int hoursToComplete;
	private String eventName;
	
	public CalendarEvent(String eventName){
		if(eventName.equalsIgnoreCase("busy") || eventName.equalsIgnoreCase("null")){
			this.progress = 0;
			this.eventLength = 0;
			this.eventId = UUID.randomUUID();
			this.eventName = eventName;
			this.hoursToComplete = 0;
		} else {
			this.progress = 0;
			this.eventLength = 5;
			this.eventId = UUID.randomUUID();
			this.eventName = eventName;
			this.hoursToComplete = 5;
		}
	}
	/**
	 * Constructor with each parameter defined
	 * @param name
	 * @param length
	 * @param hoursToComplete
	 * @param progress
	 */
	public CalendarEvent( String name, int length,int hoursToComplete, int progress){
		this.progress = progress;
		this.eventLength = length;
		this.hoursToComplete = hoursToComplete;
		this.eventId = UUID.randomUUID();
		this.eventName = name;
	}
		
	/* Setters and Getters */
	
	public int getProgress(){
		return progress;
	}
	
	public String getEventId(){
		return eventId.toString();
	}
	
	public int getEventLength(){
		return eventLength;
	}
	
	public String getName(){
		return eventName;
	}
	
	public int getHoursToComplete(){
		return hoursToComplete;
	}
	
	public void setProgress(int newProgress){
		this.progress = newProgress;
	}
	
	public void resetEventLength(int newLength){
		this.eventLength = newLength;
	}
	
	public String toString(){
		return getName();
	}
	
	public int compareTo(CalendarEvent e){
		return this.getName().compareTo(e.getName());
	}
	
	public boolean equalsTo(CalendarEvent e){
		return this.getName().equalsIgnoreCase(e.getName());
	}
	public boolean equalsTo(String string){
		return this.getName().equalsIgnoreCase(string);
				
	}
	public void updateRemainingTime(int newTime){
		hoursToComplete = newTime;
	}
	

}
