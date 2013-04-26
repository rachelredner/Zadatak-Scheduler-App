package org.zadatak.zadatak;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;

public class Activity_TodaysTasks extends Activity {
	
	// This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
	
	int lastClickedPosition;
    /********************************** ON CREATE *********************************\
    | The on create function loads the view and longs the orentation, then         |
    | populates the list for the list view for the first time                      |
    \******************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.activity_todays_tasks);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    	refreshList();
    }
    
    
    
    /******************************** REFRESH LIST ********************************\
    | This function refreshes the list if the list is ever update, for example     |
    | when a task is deleted or modified                                           |
    \******************************************************************************/
    Activity activity = null;
    private void refreshList() {
    	//String[] tasks = getTaskList();
    	ListView list = (ListView) findViewById(R.id.tasklist);
    	
    	ZadatakApp app = (ZadatakApp) getApplicationContext();
		Set<TaskBlock> tasks = app.dbman.getTasks(app.today());
    	
		if (tasks == null) {
			app.toaster("no activities regestered for today ("+app.today()+")");
			Log.v("calendar","no activities regestered for today ("+app.today()+")");
		}
		
		List<TaskBlock> taskArray = new ArrayList<TaskBlock>();
		
		for (TaskBlock taskBlock : tasks) {
			taskArray.add(taskBlock);
		}
		
		
		
		Collections.sort(taskArray, new taskBlockArrayComparitor());
		
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listitem, tasks);
        TaskBlockListAdapter adapter = new TaskBlockListAdapter(this,R.layout.listitem,taskArray);
		
        list.setAdapter(adapter);
        registerForContextMenu(list);
        activity = this;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                //edit(position);
            	lastClickedPosition = position;
            	activity.openContextMenu(parent);
            }
        });
    }
    
    public class taskBlockArrayComparitor implements Comparator<TaskBlock> {

	    public int compare(TaskBlock o1, TaskBlock o2) {
	          int datarate1=o1.startTime;
	          int datarate2=o2.startTime;

	          if(datarate1<datarate2)
	              return -1;
	          else if(datarate1>datarate2)
	              return +1;
	          else
	              return 0;
	    } 
	}
    
    /********************************* DELETE TASK ********************************\
    | This function deletes a task from the database given the id of the task in   |
    | the database                                                                 |
    \******************************************************************************/
    public void delete (int index) {
    	ZadatakApp app = (ZadatakApp) getApplicationContext();
    	app.dbman.deleteTask(app.dbman.getAllTasks().get(index).id);
    	
    	// A task has been deleted, rescedule
        app.schedule();
    	
        app.toaster("DELEATED TASK");
    	
        
        
    	refreshList();
    	
    	
    	
    }
    
    public void postpone (int index) {
    	ZadatakApp app = (ZadatakApp) getApplicationContext();
    	app.dbman.deleteTask(app.dbman.getAllTasks().get(index).id);
    	
    	// Postpone the task, passing in task of the day that should
    	// be postpoined
    	app.postpone(index);
    	
    	// A task has been deleted, rescedule
    	app.schedule();
    	
        app.toaster("POSTPONED TASK");
    	
    }
    
    /********************************** EDIT TASK *********************************\
    | This function will edit a task in the database, given the ID of the old      |
    | task in the database and a new task object to replace it in the database     |
    \******************************************************************************/
    public void edit (int index) {
        ZadatakApp app = (ZadatakApp) getApplicationContext();
        
        Intent intent = new Intent(Activity_TodaysTasks.this, Activity_NewTask.class);
        Task task = app.dbman.getAllTasks().get(index);
		task.packIntent(intent);
        
		intent.putExtra("DB_ID", task.id);
		
        Activity_TodaysTasks.this.startActivityForResult(intent,1);        
    }

    /***************************** ON ACTIVITY RESULT *****************************\
    | This function is run when the editTask (newTask with data passed to it)      |
    | activity is completed. It refreshes the list in case the editTask activity   |
    | changed any of the displayed data                                            |
    \******************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	refreshList();
    }
    
    /*************************** ON CREATE CONTEXT MENU ***************************\
    | This function creates the elements of the context menu that will be          |
    | displayed, it gets run when the context menu is registered in the oncreate   |
    | function. It creates the values of menuItems inside the context menu         |
    \******************************************************************************/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
      if (v.getId()==R.id.tasklist) {
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("Options");
        String[] menuItems = {"Edit Task","Postpone Task","Finish Task"};
        for (int i = 0; i<menuItems.length; i++) {
          menu.add(Menu.NONE, i, i, menuItems[i]);
        }
      }
    }
    
    /*************************** ON CONTEXT-ITEM SELECT ***************************\
    | This function is run whenever an item in the context menu is selected.       |
    | There is only one context menu on this activity, it handles the long poll    |
    | events on elements of the list to allow you to edit, delete, etc each of     |
    | the elements of the task list                                                |
    \******************************************************************************/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
      AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
      
      int position;
      if (info == null) {
    	  position = lastClickedPosition;
      }
      else {
    	  position = info.position;
      }
      
      int menuItemIndex = item.getItemId();
      
      switch(menuItemIndex) {
      case 0:
    	  edit (position);
    	  break;
      case 1:
    	  postpone(position);
    	  break;      
      case 2:
    	  delete(position);
    	  break;
      }
      
      return true;
    }
    
    /*************************** ON CREATE OPTIONS MENU ***************************\
    | This function is run when the options menu is created, there are no real     |
    | options for this activity yet so it does not need to do anything             |
    \******************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_todays_tasks, menu);
        return true;
    }
}