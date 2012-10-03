
public class taskScheduler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalendarEvent[] listOfEvents = new CalendarEvent[5];
		
		listOfEvents[0] = new CalendarEvent("Task 1");
		listOfEvents[1] = new CalendarEvent("Task 2", 10, 20 , 0);
		
		Scheduler sched = new Scheduler();
		for(int i = 0; i < 7; i++){
			sched.addBusyTimes(i, 20, 23);
			sched.addBusyTimes(i, 0, 8);
			
		}
		sched.addBusyTimes(0, 14, 16);
		sched.addBusyTimes(3, 16, 20);
		sched.addBusyTimes(3, 10, 14);
		sched.addBusyTimes(5, 10, 12);
		sched.addBusyTimes(2, 12, 16);
		sched.addBusyTimes(7, 8, 20);
		sched.addBusyTimes(6, 8, 20);
		
		System.out.println("Original:");
		for(int i = 0; i < 7; i++){
			sched.getCal()[i].PrintCalendar();
		}
		sched.addToQueue(listOfEvents[0]);
		sched.addToQueue(listOfEvents[1]);
		sched.scheduleEvents();
		System.out.println("After events added");
		for(int i = 0; i < 7; i++){
			sched.getCal()[i].PrintCalendar();
		}
				
	}

}
