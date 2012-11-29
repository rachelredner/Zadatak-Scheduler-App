package org.zadatak.zadatak;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Activity_TestNotification extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notification);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button createAlertButton = (Button)findViewById(R.id.alertButton);
        createAlertButton.setOnClickListener(new OnClickListener(){ public void onClick(View v){ generateAlert(); } } );
		Button cancelAlertButton = (Button)findViewById(R.id.cancelAlert);
		cancelAlertButton.setOnClickListener(new OnClickListener(){ public void onClick(View v){ cancelAlert(); } } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_test_notification, menu);
        return true;
    }
    
    PendingIntent pendingIntent;
    
    /******************************* GENERATE ALERT *******************************\
    | This function creates a pending intent that runs after 10 seconds. It is a   |
    | testing function to see how the actual activity will handle the alarms       |
    \******************************************************************************/
    private void generateAlert() {
    	//Intent myIntent = new Intent(Activity_TodaysTasks.this, Service_TaskReminder.class);
    	Intent myIntent = new Intent(Activity_TestNotification.this, Activity_AlertTask.class);
    	pendingIntent = PendingIntent.getActivity(Activity_TestNotification.this, 0, myIntent, 0);
    	
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	calendar.add(Calendar.SECOND, 10);
    	
    	alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    	
    	Toast.makeText(Activity_TestNotification.this, "Start Alarm for "+ (calendar.getTimeInMillis()-System.currentTimeMillis()) , Toast.LENGTH_LONG).show();
    }
    
    private void cancelAlert() {
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	alarmManager.cancel(pendingIntent);
    	// Tell the user about what we did.
    	Toast.makeText(Activity_TestNotification.this, "Cancel!", Toast.LENGTH_LONG).show();
    }
}
