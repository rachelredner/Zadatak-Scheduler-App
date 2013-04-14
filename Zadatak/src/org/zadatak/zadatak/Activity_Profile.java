package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TimePicker;

public class Activity_Profile extends Activity {

	TimePicker startTime;
    TimePicker endTime;
    RadioGroup schedulingSchema;
    CheckBox useSchedule;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_profile);
        
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_task);
        
        // Find all the widgets on the layout
        startTime = (TimePicker) findViewById(R.id.timepicker1);
        endTime = (TimePicker) findViewById(R.id.timepicker2);
        schedulingSchema = (RadioGroup) findViewById(R.id.scheduleoptions);
        useSchedule = (CheckBox) findViewById(R.id.usecalendar);
        
        // Set the value of each widget from the databse
        ZadatakApp app = (ZadatakApp)getApplicationContext();
        app.dbman.getSetting("startTime");
        app.dbman.getSetting("endTime");
        app.dbman.getSetting("SchedulingSchema");
        app.dbman.getSetting("useSchedule");
    	
        // Bind the save button
        Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() { public void onClick(View v) { saveSettings();} } );
		
		
    }
    
    // This function saves all of the settings into the databse
    public void saveSettings() {
    
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }
}
