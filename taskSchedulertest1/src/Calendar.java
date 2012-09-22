
public class Calendar {
	private Object[] eventsThisDay;
		
	public Calendar(){
		eventsThisDay = new Object[24];
	}
	
	public void addEvent(int index, Object event){
		eventsThisDay[index] = event;
	}
	
	public int getNextFreeSpace(){
		for(int i = 0;i< eventsThisDay.length; i++){
			if(eventsThisDay[i] == null){
				return i;
			}
		}
		return -1;
	}
	
	public int getAmountofSpace(int FreeSpace){
		int currhour = FreeSpace;
		while(eventsThisDay[currhour] == null){
			currhour++;
			if(currhour + 1 == eventsThisDay.length)
				break;
		}
		return currhour;
	}
	public void PrintCalendar(){
		System.out.print("\n");
		for(int i = 0; i < eventsThisDay.length; i++){
			if(eventsThisDay[i] == null){
				System.out.println("[null]");
			} else {
				System.out.println("[" + eventsThisDay[i] + "]");
			}
		}
		System.out.print("\n");
	}
}
