package org.zadatak.zadatak;

import java.util.ArrayList;
import java.util.List;

import org.zadatak.zadatak.Task.Attributes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
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

	  
	  
	  // CREATE A TASK
	  public long insertTask(Task task) {
	    ContentValues values = new ContentValues();
	    for (Attributes attribute : Task.Attributes.values()) {
	    	values.put(attribute.toString(), task.get(attribute));
	    }
	    long insertId = database.insert(DatabaseHelper.TABLE_NAME, null, values);
	    return insertId;
	    
	    /*Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Comment newComment = cursorToComment(cursor);
	    cursor.close();
	    return newComment;*/
	  }
	  
	  
	  // DELETE A TASK WILL BE SAVED FOR LATER
	  /*
	  public void deleteComment(Comment comment) {
	    long id = comment.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }*/

	  
	  // GET ALL TASKS
	  public List<Task> getAllComments() {
	    List<Task> comments = new ArrayList<Task>();

	    Attributes[] attributes = Task.Attributes.values();
	    
	    String[] allColumns = new String[attributes.length];
	    
	    for (int i = 0; i < attributes.length; i++) {
	    	allColumns[i] = attributes[i].toString();
	    }
	    
	    
	    
	    Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
	        allColumns, null, null, null, null, null);
	    	    
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
	  
	  // Turns a query into an actual value
	  private Task cursorToTask(Cursor cursor) {
		    Task task = new Task();
		    Attributes[] attributes = Task.Attributes.values();
		    for (int i = 1; i < attributes.length; i++) {
		    	task.set(attributes[i], cursor.getString(i));
		    }
		    return task;
		  }
}
