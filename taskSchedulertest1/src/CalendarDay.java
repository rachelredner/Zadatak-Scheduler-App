import java.util.*;


public class CalendarDay {
	private ArrayList<Object> eventsThisDay; //Holds all events for this day
		
	public CalendarDay(){
		eventsThisDay = new ArrayList<Object>(24);
		for(int i = 0; i < eventsThisDay.size(); i++){
			eventsThisDay.add(new String("null"));
		}
	}
	
	public void addEvent(int index, Object event){
		eventsThisDay.set(index, event);
	}
	
	public int getNextFreeSpace(){
		for(int i = 0;i < eventsThisDay.size(); i++){
			if(eventsThisDay.get(i) == null){
				return i;
			}
		}
		return -1;
	}
	public int getFreeSpaceThisDay(){
		int freeSpace = 0;
		for(int i = 0; i < eventsThisDay.size(); i++){
			if(eventsThisDay.get(i) == null){
				freeSpace++;
			}
		}
		return freeSpace;
	}
	/**
	 * Given a certain space hour, this will return how many hours are remaining in that slot
	 * @param startIndex hour to check on from 
	 * @return remaining hours in that slot
	 */
	public int getRemainingSpace(int startIndex){
		int currhour = startIndex;
		if(eventsThisDay.get(startIndex) != null) return -1;
		int remainingHours = 0;
		while(eventsThisDay.get(currhour) == null){
			remainingHours++;
			currhour++;
		}
		return remainingHours;
	}
	public void PrintCalendar(){
		System.out.print("\n");
		for(int i = 0; i < eventsThisDay.size(); i++){
			if(eventsThisDay.get(i) == null){
				System.out.println("[null]");
			} else {
				System.out.println("[" + eventsThisDay.get(i) + "]");
			}
		}
		System.out.print("\n");
	}
	
	public void addBusyTimes(int startTime, int endTime){
		
		for(int i = startTime; i <= endTime; i++){
			eventsThisDay.set(i, "Busy");
		}
	}
}
