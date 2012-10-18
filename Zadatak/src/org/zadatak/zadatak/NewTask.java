package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class NewTask extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	ZadatakApp app = (ZadatakApp)getApplicationContext();
        if (savedInstanceState == null) {
        	app.toaster("Null Bundle");
        }
        else if (savedInstanceState.containsKey("Data")) {
        	app.toaster("YEP: " + savedInstanceState.getString("Data"));
        }
        
    	
    	super.onCreate(savedInstanceState);
        
        
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_task);
        
        Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() { public void onClick(View v) { saveTask();} } );
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_task, menu);
        return true;
    }
    
    private void saveTask(){
    	ZadatakApp app = (ZadatakApp)getApplicationContext();
    	
    	EditText nameText = (EditText) findViewById(R.id.NameBox);
    	String name = nameText.getText().toString();
    	
    	DatePicker datePicker = (DatePicker)findViewById(R.id.datepicker);
    	String date = datePicker.getDayOfMonth() + " " + datePicker.getMonth() + " " + datePicker.getYear();
    	
    	EditText lengthText = (EditText) findViewById(R.id.estimatedlength);
    	String time = lengthText.getText().toString();
    	
    	CheckBox priorityBox = (CheckBox)findViewById(R.id.highpriority);
    	String priority = priorityBox.isChecked() ? "HIGH" : "NORMAL";
    	
    	Task task = new Task();
    	task.set(Task.Attributes.Name, name);
    	task.set(Task.Attributes.Duedate, date);
    	task.set(Task.Attributes.Hours, time);
    	task.set(Task.Attributes.Priority, priority);

    	app.dbman.insertTask(task);
    	
    	finish();
    }
}
