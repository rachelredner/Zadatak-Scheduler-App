package org.zadatak.zadatak;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	// COLUMN VALUES (N SUCH)
	public static final String TABLE_NAME = "tasks";
	public static final String TASK_ID = "_id";
	public static final String TASK_NAME = "_name";
	public static final String TASK_DEADLINE = "_deadline";
	
	// VERSION NUMBER AND DATABASE NAME
	private static final String DATABASE_NAME = "zadatak.db";
	private static final int DATABASE_VERSION = 1;
	
	// DATABASE CREATION SQL STATEMENT
	private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "("
			+ TASK_ID + " integer primary key autoincrement, "
			+ TASK_NAME + "text not null"
			+ TASK_DEADLINE + "text"
			+ ");";
	
	/**************************** DATABASE CONSTRUCTOR ****************************\
	| This is the constructor for the database class, it does not do much and      |
	| most of the work is actually done by the oncreate function of this class     |
	\******************************************************************************/
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	    db.execSQL(DATABASE_CREATE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(DatabaseHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
}
