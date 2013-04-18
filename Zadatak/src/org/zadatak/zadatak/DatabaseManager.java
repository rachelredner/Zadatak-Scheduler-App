package org.zadatak.zadatak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.zadatak.zadatak.Task.Attributes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager{
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbLayout;

	// CONSTRUCTOR FOR THE DATABSE MANAGER (CREATE A NEW DATABASE)
	public DatabaseManager(Context context) {
		dbLayout = new DatabaseHelper(context);
	}
	
	// OPEN THE DATABSE?
	public void open() throws SQLException {
		database = dbLayout.getWritableDatabase();
	}

	// CLOSE THE DATABASE?
	public void close() {
		dbLayout.close();
	}
	
	  //////////////////////////////////////////////////////////////////////////////
	 //////////////////////// TASK MANIPULATION FUNCTIONS ///////////////////////// 
	//////////////////////////////////////////////////////////////////////////////  

	/******************************** CREATE A TASK *******************************\
	| This function takes in a task value and puts all of the values into the      |
	| database for it to be found later                                            |
	\******************************************************************************/
	public Task insertTask(Task task) {
		ContentValues values = new ContentValues();
		for (Attributes attribute : Task.Attributes.values()) {
			values.put(attribute.toString(), task.get(attribute));
			System.out.println("++"+attribute.toString());
		}
		long insertId = database.insert(DatabaseHelper.TASK_TABLE_NAME, null, values);
		//return insertId;
		
		Attributes[] attributes = Task.Attributes.values();
		String[] allColumns = new String[attributes.length+1];
		allColumns[0] = DatabaseHelper.ID_COLUMN_NAME;
		for (int i = 0; i < attributes.length; i++) {
			allColumns[i+1] = attributes[i].toString();
		}
		
		
		Cursor cursor = database.query(DatabaseHelper.TASK_TABLE_NAME, allColumns, "_id = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Task newTask = cursorToTask(cursor);
		cursor.close();
		return newTask;
	}
	
	/******************************** DELETE A TASK *******************************\
	| This function takes in a task object and deletes the matching task in the    |
	| table (NOT DONE YET)                                                         |
	\******************************************************************************/
	public void deleteTask(long id) {
		System.out.println("Comment deleted with id: " + id);
		database.delete(DatabaseHelper.TASK_TABLE_NAME, DatabaseHelper.ID_COLUMN_NAME + " = " + id, null);
	}
	


	public int editTask(long id, Task task) {
		
		ContentValues values = new ContentValues();
		for (Attributes attribute : Task.Attributes.values()) {
			values.put(attribute.toString(), task.get(attribute));
			System.out.println("++"+attribute.toString());
		}
        int returncode = database.update(DatabaseHelper.TASK_TABLE_NAME, values, DatabaseHelper.ID_COLUMN_NAME + "='" + id + "'", null);
        return returncode;
    }
	
	/******************************* GET TASK BY ID *******************************\
	| This function gets a task from the database by an ID value and then returns  |
	| the whole task with all of its attributes                                    |
	\******************************************************************************/
	public Task getTaskById(long id) {
		Attributes[] attributes = Task.Attributes.values();
		String[] allColumns = new String[attributes.length+1];
		allColumns[0] = DatabaseHelper.ID_COLUMN_NAME;
		for (int i = 0; i < attributes.length; i++) {
			allColumns[i+1] = attributes[i].toString();
		}
		
		Cursor cursor = database.query(DatabaseHelper.TASK_TABLE_NAME, allColumns, "_id = " + id, null, null, null, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			Task newTask = cursorToTask(cursor);
			cursor.close();

			return newTask;
		}
		cursor.close();
		return null;
	}
	
	  
	/******************************** GET ALL TASKS *******************************\
	| This command polls the database for all of the tasks that exists in the      |
	| database and returns them in the form of a list                              |
	\******************************************************************************/
	public List<Task> getAllTasks() {
		List<Task> comments = new ArrayList<Task>();
		Attributes[] attributes = Task.Attributes.values();
		
		String[] allColumns = new String[attributes.length+1];
		allColumns[0] = DatabaseHelper.ID_COLUMN_NAME;
		for (int i = 0; i < attributes.length; i++) {
			allColumns[i+1] = attributes[i].toString();
		}
		
		Cursor cursor = database.query(DatabaseHelper.TASK_TABLE_NAME, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Task comment = cursorToTask(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}
	  
	/******************************* CURSOR TO TASK *******************************\
	| This function takes a cursor object (pointing to some element of the         |
	| database) and turns it into a Task element with the values gathered from     |
	| the database                                                                 |
	\******************************************************************************/
	private Task cursorToTask(Cursor cursor) {
		Task task = new Task();
		Attributes[] attributes = Task.Attributes.values();
		task.id = cursor.getLong(0);
		
		for (int i = 0; i < attributes.length; i++) {
			task.set(attributes[i], cursor.getString(i+1));
		}
		return task;
	}
	
	  //////////////////////////////////////////////////////////////////////////////
	 /////////////////////// SETTING MANIPULATION FUNCTIONS /////////////////////// 
	//////////////////////////////////////////////////////////////////////////////  
	
	/********************************* SET SETTING ********************************\
	| This function takes in a setting to set and a value to set it as. It first   |
	| checks to see if the value is in the database. If it is then the value is    |
	| edited to the new value. If it is not in the database then the value is      |
	| added to the database.                                                       |
	\******************************************************************************/
	public int setSetting (String setting, String value) {
		if (getSetting(setting) != null) {
			ContentValues values = new ContentValues();
			values.put("settingvalue", value);
	        int returncode = database.update(DatabaseHelper.SETTING_TABLE_NAME, values, "settingname='" + setting + "'", null);
	        return returncode;
		}
		else {
			ContentValues values = new ContentValues();
			values.put("settingname", setting);
			values.put("settingvalue", value);
			database.insert(DatabaseHelper.SETTING_TABLE_NAME, null, values);		
			return 0;
		}
	}
	
	/********************************* GET SETTING ********************************\
	| The get setting function takes in a string representing the name of the      |
	| setting and then queries the database for the row that corresponds to that   |
	| setting. It then takes the value of the setting from that row and returns it |
	\******************************************************************************/
	public String getSetting (String setting) {
		String[] allColumns = {DatabaseHelper.ID_COLUMN_NAME ,"settingname", "settingvalue"};
		
		Cursor cursor = database.query(DatabaseHelper.SETTING_TABLE_NAME, allColumns, "settingname='" + setting + "'", null, null, null, null);
		if (cursor.getCount() > 0) { // If there are one or more rows selected in the cursor then the item exists
			cursor.moveToFirst();
			String settingValue = cursor.getString(2); // Get the setting value
			cursor.close();
			return settingValue;
		}
		cursor.close();
		return null;
	}
	
	  //////////////////////////////////////////////////////////////////////////////
	 ////////////////////// SCHEDULE MANIPULATION FUNCTIONS /////////////////////// 
	//////////////////////////////////////////////////////////////////////////////  
	
	// This function gets the tasks for today and returns a map of start times to activities
	@SuppressLint("UseSparseArrays")
	public Set<TaskBlock> getTasks(long day) {
		
		// get database row with key "day"
		String[] allColumns = {DatabaseHelper.ID_COLUMN_NAME ,"day", "tasklist"};
		
		Cursor cursor = database.query(DatabaseHelper.SCHEDULE_TABLE_NAME, allColumns, "day=" + day, null, null, null, null);
		if (cursor.getCount() > 0) { // If there are one or more rows selected in the cursor then the item exists
			cursor.moveToFirst();
			String tasks = cursor.getString(2); // Get the setting value
			cursor.close();
			
			// Parse String into the hashmap
			Set<TaskBlock> taskList = new HashSet<TaskBlock>();
			
			String[] parts = tasks.split(",");
			for (String part : parts) {
				String[] elements = part.split(":");
				Integer startTime = Integer.parseInt(elements[0]);
				Integer endTime = Integer.parseInt(elements[1]);
				long id = Long.parseLong(elements[2]);
				Task task = getTaskById(id);
				
				TaskBlock taskBlock = new TaskBlock(startTime, endTime, task);
				
				
				taskList.add(taskBlock);
			}
			return taskList;
			
		}
		cursor.close();
		
		return null;
	}
	
	public int setTasks(long day, Set<TaskBlock> taskList) {
		String csv = "";
		for (TaskBlock taskBlock : taskList) {
			csv += taskBlock.startTime+":"+taskBlock.endTime+":"+taskBlock.task.id+",";
		}
		// Set database with key "day" to the value of csv
		if (getTasks(day) != null) {
			ContentValues values = new ContentValues();
			values.put("tasklist", csv);
	        int returncode = database.update(DatabaseHelper.SCHEDULE_TABLE_NAME, values, "day=" + day + "", null);
	        Log.v("Calendar","Updated day "+day+ "with tasks "+csv);
	        return returncode;
		}
		else {
			ContentValues values = new ContentValues();
			values.put("day", day);
			values.put("tasklist", csv);
			database.insert(DatabaseHelper.SCHEDULE_TABLE_NAME, null, values);
			Log.v("Calendar","Created new day "+day+ "with tasks "+csv);
			return 0;
		}
	}
}
