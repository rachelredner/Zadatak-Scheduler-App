package org.zadatak.zadatak;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
	// Database fields
	  private SQLiteDatabase database;
	  private DatabaseHelper dbLayout;
	  private String[] allColumns = { DatabaseHelper.TASK_ID,
	      DatabaseHelper.TASK_NAME,
	      DatabaseHelper.TASK_DEADLINE};

	  
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
	  public long createComment(String name, String deadline) {
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.TASK_NAME, name);
	    values.put(DatabaseHelper.TASK_DEADLINE, deadline);
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
	  public List<Comment> getAllComments() {
	    List<Comment> comments = new ArrayList<Comment>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Comment comment = cursorToComment(cursor);
	      comments.add(comment);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return comments;
	  }
}
