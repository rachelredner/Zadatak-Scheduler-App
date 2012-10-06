package org.zadatak.zadatak;

import org.zadatak.zadatak.Task.Attributes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// TABLE NAME
	public static final String TABLE_NAME = "tasks";
	
	// VERSION NUMBER AND DATABASE NAME
	private static final String DATABASE_NAME = "zadatak.db";
	private static final int DATABASE_VERSION = 1;
	
	/**************************** DATABASE CONSTRUCTOR ****************************\
	| This is the constructor for the database class, it does not do much and      |
	| most of the work is actually done by the on create function of this class    |
	\******************************************************************************/
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Attributes[] rows = Task.Attributes.values();
		String databasecreate = "create table " + TABLE_NAME + "("
				+ "_id integer primary key autoincrement,";
		for (int i = 0 ; i < rows.length; i++) {
			databasecreate += rows.toString() + " text,";
		}
		databasecreate += ");";
	    db.execSQL(databasecreate);
	}
	
	
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
}
