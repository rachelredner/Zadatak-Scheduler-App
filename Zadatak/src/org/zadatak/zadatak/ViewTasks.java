package org.zadatak.zadatak;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewTasks extends Activity {
	
	// This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);	
    	setContentView(R.layout.activity_view_tasks);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    	String[] tasks = getTaskList();

    	ListView list = (ListView) findViewById(R.id.tasklist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listitem, tasks);
        list.setAdapter(adapter);
        
        registerForContextMenu(list);
    }

    private String[] getTaskList(){
    	ZadatakApp app = (ZadatakApp) getApplicationContext();
		List<Task> tasks = app.dbman.getAllTasks();
    	
		String[] values = new String[tasks.size()];
		
		for (int i = 0; i < tasks.size(); i++) {
			String name = tasks.get(i).get(Task.Attributes.Name);
			if (name == null) name = "NULL";
			
			String duedate = tasks.get(i).get(Task.Attributes.Duedate);
			if (duedate == null) duedate = "NULL";
			
			values[i] = name + "|" + duedate;
		}
		return values;
    }
    
    /*************************** ON CREATE CONTEXT MENU ***************************\
    | This function creates the elements of the context menu that will be          |
    | displayed, it gets run when the context menu is regestered in the oncreate   |
    | function. It creates the values of menuItems inside the context menu         |
    \******************************************************************************/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
      if (v.getId()==R.id.tasklist) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(getTaskList()[info.position]);
        String[] menuItems = {"EDIT","DELETE"};
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
      
      ZadatakApp app = (ZadatakApp) getApplicationContext();
      switch(menuItemIndex) {
      case 0:
    	  app.toaster("EDIT " + info.position);
    	  break;
      case 1:
    	  app.toaster("DELETE " + info.position);
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
