

public class Scheduler {
	
	private Calendar cal;
	public Scheduler(){
		cal = new Calendar();
	}
	
	public void scheduleEvent(CalendarEvent event){
		int nextAvail = cal.getNextFreeSpace();
		
		if(nextAvail == -1){
			System.out.println("No more free space today");
			return;
		}
		
		int hoursPerDay = event.getHoursToComplete() >= event.getEventLength()? 
				(event.getHoursToComplete()/event.getEventLength()) % 24: event.getHoursToComplete() % 24;	
		if(hoursPerDay > cal.getAmountofSpace(nextAvail)){
			hoursPerDay = cal.getAmountofSpace(nextAvail) - hoursPerDay;
		}
		
		for(int i = nextAvail; i < hoursPerDay + nextAvail; i++){
			cal.addEvent(i, event.getName());
		}
		event.setProgress(hoursPerDay/event.getEventLength());
	}
	
	public void printCalendar(){
		cal.PrintCalendar();
	}
	

	
	

}
