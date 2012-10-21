package org.zadatak.zadatak;

import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@TargetApi(14)
public class BuildEventTable extends Activity {
	private Cursor mCursor = null;
	
	private static final String[] COLS = new String[] {
		CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND
	};
    
    public void getTable() {
    	Calendar startDate = Calendar.getInstance();
    	startDate.setTimeZone(TimeZone.getTimeZone("UTC"));
    	startDate.set(2012,Calendar.OCTOBER, 14);
    	long start = startDate.getTimeInMillis();
    	
    	Calendar endDate = Calendar.getInstance();
    	endDate.setTimeZone(TimeZone.getTimeZone("UTC"));
    	endDate.set(2012, Calendar.OCTOBER,21);
    	long end = endDate.getTimeInMillis();
    	
        
        mCursor = getContentResolver().query(
        		CalendarContract.Events.CONTENT_URI,
        		COLS,
        		"DTSTART > " + start + " AND DTSTART <  " + end,
        		null,
        		COLS[1]
		);
        
        mCursor.moveToFirst();      
    }
    
    public void addEvents() {
    	String title = "N/A";
    	Long start = 0L, end = 0L;
//    	switch(v.getId()) {
//	    	case R.id.next:
//	    		if(!mCursor.isLast())
//	    			mCursor.moveToNext();
//				break;
//	    	case R.id.previous:
//	    		if(!mCursor.isFirst())
//	    			mCursor.moveToPrevious();
//	    		break;
//    	}
//    	Format df = DateFormat.getDateFormat(this);
    	Format tf = DateFormat.getTimeFormat(this);
    	
    	try{
    		title = mCursor.getString(0);
    		start = mCursor.getLong(1);
    		end = mCursor.getLong(2);
    	} catch (Exception e) {
    		//ignore    	
    	}
 //   	tv.setText(title + " on " + df.format(start)+ " at " + tf.format(start) + " to " + df.format(end) + " " + tf.format(end));
 
     }
}
