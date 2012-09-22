
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
		System.out.println("Original:");
		sched.printCalendar();
		sched.scheduleEvent(listOfEvents[0]);
		System.out.println("After event 1 added");
		sched.printCalendar();
		sched.scheduleEvent(listOfEvents[1]);
		System.out.println("After event 2 added");
		sched.printCalendar();
		
		
	}

}
