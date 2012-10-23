package org.zadatak.zadatak;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Activity_TodaysTasks extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_tasks);
        
        Button createAlertButton = (Button)findViewById(R.id.alertButton);
        createAlertButton.setOnClickListener(new OnClickListener(){ public void onClick(View v){ generateAlert(); } } );
		Button cancelAlertButton = (Button)findViewById(R.id.cancelAlert);
		cancelAlertButton.setOnClickListener(new OnClickListener(){ public void onClick(View v){ cancelAlert(); } } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_todays_tasks, menu);
        return true;
    }
    
    PendingIntent pendingIntent;
    
    private void generateAlert() {
    	Intent myIntent = new Intent(Activity_TodaysTasks.this, Service_TaskReminder.class);
    	pendingIntent = PendingIntent.getService(Activity_TodaysTasks.this, 0, myIntent, 0);
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	calendar.add(Calendar.SECOND, 10);alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    	Toast.makeText(Activity_TodaysTasks.this, "Start Alarm", Toast.LENGTH_LONG).show();
    }
    
    private void cancelAlert() {
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	alarmManager.cancel(pendingIntent);
    	// Tell the user about what we did.
    	Toast.makeText(Activity_TodaysTasks.this, "Cancel!", Toast.LENGTH_LONG).show();
    }
}
