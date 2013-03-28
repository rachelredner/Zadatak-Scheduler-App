package org.zadatak.zadatak;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;

/**
 * @author Nick Timakondu Date Last Edited: 03/24/13
 * 
 *         This file contains the scheduling algorithm and holds the event queue
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class Activity_Scheduler extends Activity {

	/**
	 * This class maps days to free hours that day
	 * 
	 * @author Nick Timakondu
	 * 
	 */
	class FreeHours implements Comparable<FreeHours> {
		Calendar Day;
		int hoursfree;

		public FreeHours(Calendar n_Day) {
			Day = n_Day;
		}

		public FreeHours(Calendar n_Day, int n_freeHours) {
			Day = n_Day;
			hoursfree = n_freeHours;
		}

		// Getters and Setters
		public void setFreeHours(int n_freeHours) {
			hoursfree = n_freeHours;
		}

		public int getFreeHours() {
			return hoursfree;
		}

		public Calendar getDay() {
			return Day;
		}

		public int compareTo(FreeHours another) {
			Integer i1 = Integer.valueOf(hoursfree);
			Integer i2 = Integer.valueOf(another.getFreeHours());
			return i1.compareTo(i2);
		}

		public String toString() {
			DateFormat df = DateFormat.getDateInstance();
			return df.format(Day.getTime()) + ": " + hoursfree;
		}
	}

	/**
	 * Allows sorting through Tasks by due date then by priority
	 */
	static Comparator<Task> DATE_ORDER = new Comparator<Task>() {
		public int compare(Task t1, Task t2) {
			Calendar c1, c2;
			c1 = c2 = Calendar.getInstance((TimeZone.getTimeZone("UTC")));
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy",
					Locale.ENGLISH);
			try {
				c1.setTime(df.parse(t1.get(Task.Attributes.Duedate)));
				c2.setTime(df.parse(t2.get(Task.Attributes.Duedate)));
			} catch (ParseException e) {
				Log.e("Exception", e.getMessage());
				e.printStackTrace();
			}
			// Due date is equal
			if (c1.compareTo(c2) == 0) {
				Integer t1priority = t1.get(Task.Attributes.Priority)=="TRUE"?1:0;
				Integer t2priority = t2.get(Task.Attributes.Priority)=="TRUE"?1:0;
				return t1priority.compareTo(t2priority);

			} else
				return c1.compareTo(c2);
		}

	};

	protected Cursor mCursor = null; // Cursor for getting calendar events
	protected ContentResolver contentResolver = null; // Accessing this app's
														// contentResolver
	protected List<FreeHours> listOfDays = new ArrayList<FreeHours>();
	// A list of days and their corresponding free hours
	protected PriorityQueue<Task> queue = null; // Store a list of all the tasks
												// that have to be scheduled

	/**
	 * Used for getting Calendar Inputs
	 */
	public static final String[] COLS = new String[] {
			CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART,
			CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY,
			CalendarContract.Events.CALENDAR_DISPLAY_NAME,
			CalendarContract.Events.DURATION

	};

	/**
	 * Initialize variables
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ZadatakApp app = (ZadatakApp) getApplicationContext();
		List<Task> tasks = app.dbman.getAllTasks();

		for (Task t : tasks) {
			Log.d("Tasks", "task: " + t);
		}
		contentResolver = getContentResolver();
		// Organize the queue based on date
		queue = new PriorityQueue<Task>(10, DATE_ORDER);
		queue.addAll(tasks);

	}

	@Override
	public void onStart() {
		super.onStart();
		scheduleEvents();
	}

	/**
	 * Populate list of Days with appropriate data
	 */
	public void populateListofDays() {
		listOfDays.clear();
		// Initialize class to get busy times
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		DateFormat df = DateFormat.getDateInstance();
		// TODO: Change forecasting based on conversation with Asher and Rachel
		int days = 10;
		int hoursFree;
		for (int i = 0; i < days; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1); // Advance day by one
			Calendar newCal = Calendar.getInstance();
			newCal.setTime(cal.getTime());
			;
			hoursFree = hoursFreethisDay(newCal); // Get free hours this day
			// Log.d("NickDebug", "Day: " + df.format(newCal.getTime()) +
			// " FreeHours: " + hoursFree);
			FreeHours fh = new FreeHours(newCal, hoursFree); // Assign free
																// hours to this
																// day
			listOfDays.add(fh);
		}
	}

	/**
	 * Schedule all the events in the queue by adding one hour at a time to days
	 * that have the most free days available. Continue until all events have
	 * been scheduled.
	 */
	public void scheduleEvents() {
		Calendar nextOpenDay;
		DateFormat localdf = SimpleDateFormat.getDateInstance();
		// Continue Algorithm until all events have been scheduled.
		// Add priority so that higher priority events get scheduled first
		while (!queue.isEmpty()) {
			Task currTask = queue.remove();
			String strHoursRemaining = currTask.get(Task.Attributes.Hours);
			if (strHoursRemaining == "") strHoursRemaining = "4";
			int hrsRemaining = Integer.parseInt(strHoursRemaining);// Get remaining hours for
													// this task
			Log.d("NickDebug", "task hours remaining: " + hrsRemaining);
			while (hrsRemaining > 0) {
				// Get the date from the task
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy",
						Locale.ENGLISH);
				Calendar dueDate = Calendar.getInstance(TimeZone
						.getTimeZone("UTC"));
				try {
					Log.d("NickDebug",
							"DueDate: " + currTask.get(Task.Attributes.Duedate));
					dueDate.setTime(df.parse(currTask
							.get(Task.Attributes.Duedate)));
					Log.d("NickDebug",
							"DueDate after parse: "
									+ localdf.format(dueDate.getTime()));
				} catch (ParseException e) {
					Log.d("ParseException", e.getMessage());
				}

				// If event has a deadline, find the day with the most open
				// spots given that deadline
				nextOpenDay = maxDayHourRemaining(dueDate);
				Log.d("NickDebug",
						"Next Open Day: " + df.format(nextOpenDay.getTime()));
				pushToCalendar(nextOpenDay, currTask);
				// currTask.set(Task.Attributes.Hours,
				// Integer.toString(Integer.parseInt(currTask.get(Task.Attributes.Hours))
				// - 1));
				hrsRemaining--;

			}
		}
	}

	/**
	 * Returns the day with the most free hours remaining between now and the
	 * end of time (end of calendar)
	 * 
	 * @return day index with most free hours remaining
	 */
	protected Calendar maxDayHourRemaining() {
		populateListofDays();
		Collections.sort(listOfDays);
		return listOfDays.get(listOfDays.size() - 1).getDay();
	}

	/**
	 * Returns the day with the most free hours remaining between now and endDay
	 * 
	 * @param endDay
	 *            The upper limit to look for free time
	 * @return day index with the most free hours remaining given these
	 *         constraints
	 */
	protected Calendar maxDayHourRemaining(Calendar endDay) {
		populateListofDays();
		Log.d("NickDebug", "listOfDays size: " + listOfDays.size());
		Collections.sort(listOfDays);
		DateFormat df = DateFormat.getDateInstance();

		for (int i = listOfDays.size() - 1; i > 0; i--) {
			FreeHours fh = listOfDays.get(i);
			Calendar thisDay = fh.getDay();
			Log.d("NickDebug", "thisDay: " + df.format(thisDay.getTime())
					+ " i: " + i);
			if (thisDay.before(endDay)) {
				return listOfDays.get(i).getDay();
			}
		}
		return null;
	}

	/**
	 * This class returns a list of events up to a certain date
	 * 
	 * @param endDate
	 * @return list of CalendarEvents
	 */
	protected List<CalendarEvent> getEvents(Calendar endDate) {
		List<CalendarEvent> list = new ArrayList<CalendarEvent>();
		Calendar startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		long start = startDate.getTimeInMillis();
		long end = endDate.getTimeInMillis();

		mCursor = contentResolver.query(CalendarContract.Events.CONTENT_URI,
				COLS, "DTSTART > " + start + " AND DTEND < " + end, null,
				COLS[1]);

		mCursor.moveToFirst();
		while (!mCursor.isLast() && mCursor.getCount() > 0) {
			String title = mCursor.getString(0);
			long dTStart = mCursor.getLong(1);
			long dTEnd = mCursor.getLong(2);
			// Incorporate this information into a class.
			Calendar eventStartDate = Calendar.getInstance(TimeZone
					.getTimeZone("UTC"));
			Calendar eventEndDate = Calendar.getInstance(TimeZone
					.getTimeZone("UTC"));
			eventStartDate.setTimeInMillis(dTStart);
			eventEndDate.setTimeInMillis(dTEnd);

			CalendarEvent calEvent = new CalendarEvent(title, eventStartDate,
					eventEndDate);
			list.add(calEvent);
			mCursor.moveToNext();
		}
		mCursor.close();
		return list;
	}

	/**
	 * Returns all the events on this specific day.
	 * 
	 * @param endDate
	 * @return
	 */
	protected List<CalendarEvent> getEventsThisDay(Calendar endDate) {
		List<CalendarEvent> list = new ArrayList<CalendarEvent>();
		long end = endDate.getTimeInMillis();
		mCursor = contentResolver.query(CalendarContract.Events.CONTENT_URI,
				COLS, "DTSTART = " + end + " AND DTEND = "
						+ (end + (24 * 60 * 60 * 1000)), null, COLS[1]);

		mCursor.moveToFirst();
		Log.d("NickDebug", "Cursor size: " + mCursor.getCount());

		while (!mCursor.isLast() && mCursor.getCount() > 0) {
			String title = mCursor.getString(0);
			long dTStart = mCursor.getLong(1);
			long dTEnd = mCursor.getLong(2);
			// TODO: Figure out how to incorporate this information into a
			// class.
			Calendar eventStartDate = Calendar.getInstance(TimeZone
					.getTimeZone("UTC"));
			Calendar eventEndDate = Calendar.getInstance(TimeZone
					.getTimeZone("UTC"));
			eventStartDate.setTimeInMillis(dTStart);
			eventEndDate.setTimeInMillis(dTEnd);

			CalendarEvent calEvent = new CalendarEvent(title, eventStartDate,
					eventEndDate);
			list.add(calEvent);
			mCursor.moveToNext();
		}
		mCursor.close();
		return list;
	}

	/**
	 * Find the remaining free hours this day;
	 * 
	 * @param day
	 * @return free hours
	 */
	protected int hoursFreethisDay(Calendar day) {
		day.set(Calendar.HOUR_OF_DAY, 8);
		day.set(Calendar.MINUTE, 0);
		
		/////////DEBUG//////////////
		printCalendars();
		////////////////////////////
		
		long date = day.getTimeInMillis();
		DateFormat df = DateFormat.getDateTimeInstance();
		Log.d("NickDebug","hoursFreethisDay: Day: " + df.format(day.getTime()));
		long dTStart, dTEnd, diff;
		int freeHours = 16;
		mCursor = contentResolver.query(CalendarContract.Events.CONTENT_URI,
				COLS, "DTSTART > " + date + " AND DTEND < "
						+ (date + 16 * 60 * 60 * 1000), null, COLS[1]);
		Log.d("NickDebug", "hoursFreethisDay: " + mCursor.getCount() + " rows");
		mCursor.moveToFirst();
		while (!mCursor.isAfterLast()) {
			Log.d("NickDebug", "Calendar: " + mCursor.getString(4));
			Log.d("NickDebug", "Event Name:" + mCursor.getString(0) );
			Log.d("NickDebug", "Duration: " + mCursor.getString(5));
			
			dTStart = mCursor.getLong(1);
			dTEnd = mCursor.getLong(2);
			diff = dTEnd - dTStart;
			diff = diff / (1000 * 60 * 60);
			freeHours -= diff;
			mCursor.moveToNext();
		}
		return freeHours;
	}
	
	private void printCalendars(){
		mCursor = contentResolver.query(CalendarContract.Calendars.CONTENT_URI,
				new String[]{CalendarContract.Calendars.ACCOUNT_NAME,
								CalendarContract.Calendars.NAME},
				null, null, null);
		mCursor.moveToFirst();
		while(!mCursor.isAfterLast()){
			Log.d("NickDebug", "Account: " + mCursor.getString(0));
			Log.d("NickDebug", "Name: " + mCursor.getString(1));
			mCursor.moveToNext();
		}
	}

	/**
	 * Add an event to the calendar
	 * 
	 * @param day
	 * @param hour
	 * @param name
	 * @return
	 */
	protected String addEvent(Calendar day, int hour, String name) {
		day.set(Calendar.HOUR_OF_DAY, hour);
		long start = day.getTimeInMillis();
		day.set(Calendar.HOUR_OF_DAY, hour + 1);
		long end = day.getTimeInMillis();

		ContentValues values = new ContentValues();
		values.put(CalendarContract.Events.DTSTART, start);
		values.put(CalendarContract.Events.DTEND, end);
		values.put(CalendarContract.Events.TITLE, name);
		values.put(CalendarContract.Events.CALENDAR_ID, 1);
		values.put(CalendarContract.Events.EVENT_TIMEZONE, "UTC");

		Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI,
				values);

		String eventID = uri.getLastPathSegment();
		return eventID;
	}

	/**
	 * Find the first available hour of a given day then add that task to the
	 * calendar
	 * 
	 * @param day
	 * @param task
	 */
	protected void pushToCalendar(Calendar day, Task task) {

		int freehour = getFreeHour(day);
		addEvent(day, freehour, task.get(Task.Attributes.Name));
	}

	/**
	 * Find the first available hour in a given day
	 * 
	 * @param day
	 * @return
	 */
	protected int getFreeHour(Calendar day) {
		String[] hours = new String[24];

		// Add Sleep time from midnight to 8am
		for (int i = 0; i < 8; i++)
			hours[i] = "Busy";
		// Add sleep time from 10pm to midnight
		for (int i = 22; i < 24; i++)
			hours[i] = "Busy";

		List<CalendarEvent> eventsThisDay = getEventsThisDay(day);

		for (CalendarEvent e : eventsThisDay)
			for (int i = e.startDate.get(Calendar.HOUR_OF_DAY); i < e.endDate
					.get(Calendar.HOUR_OF_DAY); i++)
				hours[i] = e.name;

		int freehour = 0;
		for (int i = 0; i < 24; i++) {
			if (hours[i] == null) {
				freehour = i;
				break;
			}
		}
		return freehour;
	}
}
