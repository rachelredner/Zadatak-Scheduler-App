package org.zadatak.zadatak;

import java.util.List;

import android.content.ClipData.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<Task> {
	
	private List<Task> tasks;
	
	public TaskListAdapter(Context context, int textViewResourceId) {
	    super(context, textViewResourceId);
	}
	
	public TaskListAdapter(Context context, int resource, List<Task> items) {
	    super(context, resource, items);
	    this.tasks = items;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

	    View v = convertView;

	    
	    // If there is no target layout selected pick the custom listitem.xml layout
	    if (v == null) {
	        LayoutInflater vi;
	        vi = LayoutInflater.from(getContext());
	        v = vi.inflate(R.layout.listitem, null);
	    }

	    Task task = tasks.get(position);

	    
	    if (task != null) {

	        TextView nameBox = (TextView) v.findViewById(R.id.displayTaskName);
	        TextView duedateBox = (TextView) v.findViewById(R.id.displayDueDate);
	        
	        if (nameBox != null) {
	        	String taskName = task.get(Task.Attributes.Name);
	        	if (taskName != null) {
	        		nameBox.setText(taskName);
	        	}
	        }
	        if (duedateBox != null) {
	        	String dueDate = task.get(Task.Attributes.Duedate);
	        	if (dueDate != null) {
	        		duedateBox.setText(dueDate);
	        	}
	        }

	    }

	    return v;

	}

}
