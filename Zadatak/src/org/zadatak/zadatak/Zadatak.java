package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Zadatak extends Activity {
	
	/********************************** ON CREATE *********************************\
	| The onCreate function is run when the program is loaded. It handles all of   |
	| the assigning of click listeners and other events we need for the main       |
	| zadatak activity                                                             |
	\******************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadatak);
        
        // Grab the new task button and assign a click listener to load the new task activity when clicked
		Button newtaskButton = (Button)findViewById(R.id.newtask);
		newtaskButton.setOnClickListener(new OnClickListener(){ public void onClick(View v){ goToNewTask(); } } );
		// Repeat this for the task list
		Button tasklistButton = (Button) findViewById(R.id.tasklist);
		tasklistButton.setOnClickListener(new OnClickListener() { public void onClick(View v) { goToTaskList();} } );
		// and repeat again for 'todays tasks'
		Button todaysTasks = (Button) findViewById(R.id.todaystasks);
		todaysTasks.setOnClickListener(new OnClickListener() { public void onClick(View v) { goToTodaysTasks();} } );
		// and repeat again for 'todays tasks'
		Button profilepage = (Button) findViewById(R.id.myprofile);
		profilepage.setOnClickListener(new OnClickListener() { public void onClick(View v) { goToProfilePage();} } );
		
		
    }
    /**************************** ON CREATE OPTION MENU ***************************\
    | This adds the menu button menu to the activity, it can be changed in         |
    | res/menu/activity_zadatak.xml                                                |
    \******************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_zadatak, menu);
        return true;
    }
    /******************************* GO TO NEW TASK *******************************\
    | This function is called when the user clicks on the new task button, it      |
    | brings the user to an activity where they can input a new task into          |
    | Zadatak. The new task will then appear on the task list.                     |
    \******************************************************************************/
    private void goToNewTask(){
    	Intent intent = new Intent(Zadatak.this, NewTask.class);
		Zadatak.this.startActivity(intent);
    }
    /******************************* GO TO TASK LIST ******************************\
    | This function is called when the view task list button is clicked on the     |
    | screen. It calls a new activity (task list) up to show the user the list of  |
    | tasks he or she has                                                          |
    \******************************************************************************/
    private void goToTaskList(){
    	/*Context context = getApplicationContext();
    	CharSequence text = "GO TO TASK LIST";
    	int duration = Toast.LENGTH_SHORT;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();*/
    	
    	Intent intent = new Intent(Zadatak.this, ViewTasks.class);
		Zadatak.this.startActivity(intent);
		
    }
    /***************************** GO TO TODAYS TASKS *****************************\
    | This function, like the other go to functions, is called when the user       |
    | clicks the view todays tasks button. It calls up the todays task activity.   |
    \******************************************************************************/
    private void goToTodaysTasks() {
    	Context context = getApplicationContext();
    	CharSequence text = "TODAYS TASKS";
    	int duration = Toast.LENGTH_SHORT;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    	
    	/*
    	Intent intent = new Intent(Zadatak.this, NewTask.class);
		Zadatak.this.startActivity(intent);
		*/
    }
    
    private void goToProfilePage() {
    	Intent intent = new Intent(Zadatak.this, Profile.class);
		Zadatak.this.startActivity(intent);
    }
}
