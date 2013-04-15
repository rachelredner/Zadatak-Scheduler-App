package org.zadatak.zadatak;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
		List<Task> tasks = app.dbman.getAllTasks();
    	
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listitem, tasks);
        
        TaskListAdapter adapter = new TaskListAdapter(this,R.layout.listitem,tasks);
		
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