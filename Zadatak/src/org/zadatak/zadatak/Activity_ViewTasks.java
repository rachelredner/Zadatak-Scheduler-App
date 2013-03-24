package org.zadatak.zadatak;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Activity_ViewTasks extends Activity {
	
	// This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
	
	
    /********************************** ON CREATE *********************************\
    | The on create function loads the view and longs the orentation, then         |
    | populates the list for the list view for the first time                      |
    \******************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);	
    	setContentView(R.layout.activity_view_tasks);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    	refreshList();
    }
    
    /******************************** REFRESH LIST ********************************\
    | This function refreshes the list if the list is ever update, for example     |
    | when a task is deleted or modified                                           |
    \******************************************************************************/
    private void refreshList() {
    	String[] tasks = getTaskList();
    	ListView list = (ListView) findViewById(R.id.tasklist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listitem, tasks);
        list.setAdapter(adapter);
        
        registerForContextMenu(list);
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                edit(position);
            }
        });
    }
    
    /******************************** GET TASK LIST *******************************\
    | This function gets a list of all the tasks in the database and returns a     |
    | string based on some feature of each task. Currently it is                   |
    | Name + | + date                                                              |
    \******************************************************************************/
    private String[] getTaskList(){
    	ZadatakApp app = (ZadatakApp) getApplicationContext();
		List<Task> tasks = app.dbman.getAllTasks();
    	
		String[] values = new String[tasks.size()];
		
		for (int i = 0; i < tasks.size(); i++) {
			String name = tasks.get(i).get(Task.Attributes.Name);
			if (name == null) name = "NULL";
			
			String duedate = tasks.get(i).get(Task.Attributes.Duedate);
			if (duedate == null) duedate = "NULL";
			
			values[i] = name + " | " + duedate;
		}
		return values;
    }
    
    /********************************* DELETE TASK ********************************\
    | This function deletes a task from the database given the id of the task in   |
    | the database                                                                 |
    \******************************************************************************/
    public void delete (int index) {
    	ZadatakApp app = (ZadatakApp) getApplicationContext();
    	app.dbman.deleteTask(app.dbman.getAllTasks().get(index).id);
    	refreshList();
    }
    
    /********************************** EDIT TASK *********************************\
    | This function will edit a task in the database, given the ID of the old      |
    | task in the database and a new task object to replace it in the database     |
    \******************************************************************************/
    public void edit (int index) {
        ZadatakApp app = (ZadatakApp) getApplicationContext();
        
        Intent intent = new Intent(Activity_ViewTasks.this, Activity_NewTask.class);
        Task task = app.dbman.getAllTasks().get(index);
		task.packIntent(intent);
        
		intent.putExtra("DB_ID", task.id);
		
        Activity_ViewTasks.this.startActivityForResult(intent,1);        
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
        String[] menuItems = {"Edit Task","Delete Task"};
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
      int menuItemIndex = item.getItemId();
      
      switch(menuItemIndex) {
      case 0:
    	  edit (info.position);
    	  break;
      case 1:
    	  delete(info.position);
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
        getMenuInflater().inflate(R.menu.activity_view_tasks, menu);
        return true;
    }
}
