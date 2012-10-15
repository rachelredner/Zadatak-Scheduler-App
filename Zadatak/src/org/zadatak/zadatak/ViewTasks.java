package org.zadatak.zadatak;

import java.util.List;

import android.os.Bundle;
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

public class ViewTasks extends ListActivity {
	
	// This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    	String[] tasks = getTaskList();
    	
		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);
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
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    /*
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.red:
                //editNote(info.id);
                return true;
            case R.id.blue:
                //deleteNote(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }*/
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_tasks, menu);
        return true;
    }
}
