package org.zadatak.zadatak;



import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;




public class Activity_Zadatak extends Activity {
	
	/********************************** ON CREATE *********************************\
	| The onCreate function is run when the program is loaded. It handles all of   |
	| the assigning of click listeners and other events we need for the main       |
	| zadatak activity                                                             |
	\******************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
		
		// Open the database
		ZadatakApp app = (ZadatakApp) getApplicationContext();
		app.dbman.open();
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
    	Intent intent = new Intent(Activity_Zadatak.this, Activity_NewTask.class);
		Activity_Zadatak.this.startActivity(intent);
    }
    
    /******************************* GO TO TASK LIST ******************************\
    | This function is called when the view task list button is clicked on the     |
    | screen. It calls a new activity (task list) up to show the user the list of  |
    | tasks he or she has                                                          |
    \******************************************************************************/
    private void goToTaskList(){
    	Intent intent = new Intent(Activity_Zadatak.this, Activity_ViewTasks.class);
		Activity_Zadatak.this.startActivity(intent);		
    }
    
    /***************************** GO TO TODAYS TASKS *****************************\
    | This function, like the other go to functions, is called when the user       |
    | clicks the view todays tasks button. It calls up the todays task activity.   |
    \******************************************************************************/
    private void goToTodaysTasks() {
    	Intent intent = new Intent(Activity_Zadatak.this, Activity_TodaysTasks.class);
    	Activity_Zadatak.this.startActivity(intent);
    }
    
    private void goToProfilePage() {
    	Intent intent = new Intent(Activity_Zadatak.this, Activity_Profile.class);
		Activity_Zadatak.this.startActivity(intent);
    }
}
