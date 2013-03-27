package org.zadatak.zadatak;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/****************************** FILE DESCRIPTION ******************************\
| The TaskListAdapter class is used in order to generate a list view of        |
| tasks. It can be passed a list of tasks and it will create custom layouts    |
| for the list view, instead of just a single string being represented in it.  |
| You can call this in the same way that you would call a normal ArrayAdapter  |
| except there is no need to specify the type <Task> because that has already  |
| been stated in this class                                                    |
\******************************************************************************/
public class TaskListAdapter extends ArrayAdapter<Task> {
	
	private List<Task> tasks;
	
	/********************************* CONSTRUCTOR ********************************\
	| The constructor function takes in a context (usually the activity that       |
	| called it, a layout resource (R.layout.listitem), and a list of Task         |
	| objects. It initializes the TaskList adapter so it can be passed into a list |
	| view                                                                         |
	\******************************************************************************/
	public TaskListAdapter(Context context, int resource, List<Task> items) {
		super(context, resource, items);
		this.tasks = items;

	}
	
	/********************************** GET VIEW **********************************\
	| The get view function is used to take the elements of the list and put them  |
	| into a view. It is an override function from the original ArrayAdapter       |
	| class. This function takes the view given, if no view is given then it uses  |
	| the listitem.xml view, and fills in all the components of it using the       |
	| values of the task object that was passed to it.                             |
	\******************************************************************************/
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
			// Get the elements of the layout to write to
			TextView nameBox = (TextView) v.findViewById(R.id.displayTaskName);
			TextView duedateBox = (TextView) v.findViewById(R.id.displayDueDate);
			
			// Fill in the values of the task object
			if (nameBox != null) {
				String taskName = task.get(Task.Attributes.Name);
				if (taskName != null) { nameBox.setText(taskName); }
			}
			if (duedateBox != null) {
				String dueDate = task.get(Task.Attributes.Duedate);
				if (dueDate != null) { 	duedateBox.setText("Due on: "+dueDate);	}
			}
		}
		return v;
	}
}
