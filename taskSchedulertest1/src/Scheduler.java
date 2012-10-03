import java.util.concurrent.ConcurrentLinkedQueue;



public class Scheduler {
	
	private CalendarDay[] cal;
	private ConcurrentLinkedQueue<CalendarEvent> queue;
	
	public Scheduler(){
		cal = new CalendarDay[7];
		for(int i = 0; i < cal.length; i++){
			cal[i] = new CalendarDay();
		}
	}
	
	public void scheduleEvents(){
		CalendarDay currDay = null;
		while(!queue.isEmpty()){
			CalendarEvent event = queue.remove();
			int hrsRemaining = event.getHoursToComplete();
			while(hrsRemaining > 0){
				if(event.getEventLength() == -1)
					currDay = cal[maxDayHourRemaining()];
				else
					currDay = cal[maxDayHourRemaining(event.getEventLength())];
				
				currDay.addEvent(currDay.getNextFreeSpace(), event);
			}
		}
	}
	
	public CalendarDay[] getCal(){
		return cal;
	}
	public void addBusyTimes(int day, int startHour, int endHour){
		CalendarDay currDay = cal[day];
		currDay.addBusyTimes(startHour, endHour);		
	}
	
	public void initQueue(){
		queue = new ConcurrentLinkedQueue<CalendarEvent>();
	}
	
	public void addToQueue(CalendarEvent event){
		queue.add(event);
	}
	
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
