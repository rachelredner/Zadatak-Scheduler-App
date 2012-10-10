package org.zadatak.zadatak;

import java.util.ArrayList;
import java.util.List;

import org.zadatak.zadatak.Task.Attributes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
		long insertId = database.insert(DatabaseHelper.TABLE_NAME, null, values);
		//return insertId;
		
		String[] allColumns = new String[Task.Attributes.values().length];
		
		for (int i = 0; i < Task.Attributes.values().length; i++) {
			allColumns[i] = Task.Attributes.values()[i].toString();
		}
		
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, allColumns, "_id = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Task newTask = cursorToTask(cursor);
		cursor.close();
		return newTask;
	}
	
	/******************************** DELETE A TASK *******************************\
	| This function takes in a task object and deletes the matching task in the    |
	| table (NOT DONE YET)                                                         |
	\******************************************************************************/
	/*
	public void deleteComment(Comment comment) {
		long id = comment.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}*/
	
	  
	/******************************** GET ALL TASKS *******************************\
	| This command polls the database for all of the tasks that exists in the      |
	| database and returns them in the form of a list                              |
	\******************************************************************************/
	public List<Task> getAllTasks() {
		List<Task> comments = new ArrayList<Task>();
		Attributes[] attributes = Task.Attributes.values();
		
		String[] allColumns = new String[attributes.length];
		
		for (int i = 0; i < attributes.length; i++) {
			allColumns[i] = attributes[i].toString();
		}
		
		Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, allColumns, null, null, null, null, null);
		
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
		for (int i = 0; i < attributes.length; i++) {
			task.set(attributes[i], cursor.getString(i));
		}
		return task;
	}
}
