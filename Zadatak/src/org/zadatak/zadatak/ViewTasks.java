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
    	
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, tasks);
        list.setAdapter(adapter);
        
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
