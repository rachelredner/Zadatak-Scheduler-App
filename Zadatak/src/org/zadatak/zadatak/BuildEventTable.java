package org.zadatak.zadatak;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.provider.CalendarContract;
/**
 * This class interfaces with the Calendar Table
 * @author Nick Timakondu
 *
 */
@TargetApi(14)
public class BuildEventTable extends Activity {
	protected Cursor mCursor = null;
		
	public static final String[] COLS = new String[] {
		CalendarContract.Events.TITLE,
		CalendarContract.Events.DTSTART,
		CalendarContract.Events.DTEND,
		CalendarContract.Events.ALL_DAY,
		
	};
	 
	public BuildEventTable(){
		//Empty Constructor
	}
    
	/**
	 * This class returns a list of events up to a certain date
	 * @param endDate 
	 * @return list of CalendarEvents
	 */
	public List<CalendarEvent> getEvents(Calendar endDate){
		List<CalendarEvent> list = new ArrayList<CalendarEvent>();
		Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		long start = startDate.getTimeInMillis();
		long end = endDate.getTimeInMillis();
		
		mCursor = getContentResolver().query(
				CalendarContract.Events.CONTENT_URI,
				COLS,
				"DTSTART > " + start + " AND DTEND < " + end,
				null,
				COLS[1]
		);
		
		mCursor.moveToFirst();
		while(!mCursor.isLast()){
			String title = mCursor.getString(0);
			long dTStart = mCursor.getLong(1);
			long dTEnd = mCursor.getLong(2);
			//TODO: Figure out how to incorporate this information into a class.
			Calendar eventStartDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			Calendar eventEndDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			eventStartDate.setTimeInMillis(dTStart);
			eventEndDate.setTimeInMillis(dTEnd);
			
			CalendarEvent calEvent = new CalendarEvent(title,eventStartDate, eventEndDate);
			list.add(calEvent);			
			mCursor.moveToNext();
		}
		
		return list;
	}
	/**
	 * Find the remaining free hours this day;
	 * @param day
	 * @return free hours
	 */
	public int hoursFreethisDay(Calendar day){
		long date = day.getTimeInMillis();
		long dTStart, dTEnd, diff;
		int FreeHours = 16;
		mCursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI,
				COLS, 
				"DTSTART = " + date + " AND DTEND = " + date,
				null,
				COLS[1]
		);
		
		mCursor.moveToFirst();
		while(!mCursor.isAfterLast()){
			dTStart = mCursor.getLong(1);
			dTEnd = mCursor.getLong(2);
			diff = dTEnd - dTStart;
			diff = diff/(1000*60*60);
			FreeHours -=diff;
		}
		return FreeHours;		
	}
 
}
