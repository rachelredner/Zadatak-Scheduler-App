package org.zadatak.zadatak;

import java.util.List;

import android.os.Bundle;
import android.app.ListActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class ViewTasks extends ListActivity {
	
	// This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	ZadatakApp app = (ZadatakApp) getApplicationContext();
		List<Task> tasks = app.dbman.getAllTasks();
    	
		String[] values = new String[tasks.size()];
		
		for (int i = 0; i < tasks.size(); i++) {
			values[i] = tasks.get(i).get(Task.Attributes.Name);
			if (values[i] == null) values[i] = "NULL";
		}
		
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_tasks, menu);
        return true;
    }
}
