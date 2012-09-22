import java.util.UUID;

/**
 * 
 */

/**
 * @author nickt_000
 *
 */
public class CalendarEvent {

	private int progress;
	private UUID eventId;
	//EventLength is defined in days
	private int eventLength;
	private int hoursToComplete;
	private String eventName;
	
	public CalendarEvent(String eventName){
		this.progress = 0;
		this.eventLength = 14;
		this.eventId = UUID.randomUUID();
		this.eventName = eventName;
		this.hoursToComplete = 5;
	}
	
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

}
