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
import android.widget.SeekBar;

public class Activity_NewTask extends Activity {

	EditText   nameText;
	DatePicker datePicker;
	EditText   lengthText;
	SeekBar    progressBar;
	CheckBox   priorityBox;
	
	long id;
	boolean editmode = false;
	
	/********************************** ON CREATE *********************************\
	| This function finds the objects on the activites view and sets them to the   |
	| global variables. It also checks to see if any data has been passed to it    |
	| in the bundle, if data exists then it extracts the data and fills out the    |
	| view to match the data passed                                                |
	\******************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
       	super.onCreate(savedInstanceState);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_task);
        
        nameText = (EditText) findViewById(R.id.NameBox);
    	datePicker = (DatePicker)findViewById(R.id.datepicker);
    	lengthText = (EditText) findViewById(R.id.estimatedlength);
    	progressBar = (SeekBar) findViewById(R.id.currentprogress);
    	priorityBox = (CheckBox)findViewById(R.id.highpriority);
    	
    	// If a task was passed in, attempt to load it
    	Bundle passedData = getIntent().getExtras();
        if (passedData != null) {
        	Task task = new Task();
        	task.extractBundle(passedData);
        	
        	task.get(Task.Attributes.Name, nameText);
        	task.get(Task.Attributes.Duedate, datePicker);
        	task.get(Task.Attributes.Hours, lengthText);
        	task.get(Task.Attributes.Progress, progressBar);
        	task.get(Task.Attributes.Priority, priorityBox);
        	
        	id = passedData.getLong("DB_ID");
        	editmode = true;
        }   
    	
        Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() { public void onClick(View v) { saveTask();} } );
        
    }

    /*************************** ON CREATE OPTIONS MENU ***************************\
    | This function creates the options menu at the bottom of the screen, it       |
    | probably can be removed due to the options menu not currently having any     |
    | task                                                                         |
    \******************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_task, menu);
        return true;
    }
    
    /********************************** SAVE TASK *********************************\
    | This function will save a task to the databsae. If the activity is editing   |
    | a task that was passed to it then the activity is saved over the old         |
    | activity. If the activity is not editing a task then it will save the task   |
    | as a new task in the database                                                |
    \******************************************************************************/
    private void saveTask(){
    	ZadatakApp app = (ZadatakApp)getApplicationContext();
    	
    	Task task = new Task();
    	task.set(Task.Attributes.Name, nameText);
    	task.set(Task.Attributes.Duedate, datePicker);
    	task.set(Task.Attributes.Hours, lengthText);
    	task.set(Task.Attributes.Progress, progressBar);
    	task.set(Task.Attributes.Priority, priorityBox);
    	
    	// Save it to the database either as a new task or over an old task
    	if (editmode) { app.dbman.editTask(id, task); }
    	else { app.dbman.insertTask(task); }
    	
    	// A task has been added or changed, rescedule
    	app.schedule();
    	
    	app.toaster("NEW / EDITED TASK");
    	
    	// Quit the activity
    	finish();
    }
}
