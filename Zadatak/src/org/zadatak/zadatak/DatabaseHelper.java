package org.zadatak.zadatak;

import org.zadatak.zadatak.Task.Attributes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// TABLE NAMES
	public static final String TASK_TABLE_NAME = "tasks";
	public static final String SETTING_TABLE_NAME = "settings";
	public static final String SCHEDULE_TABLE_NAME = "schedules";
	
	// VERSION NUMBER AND DATABASE NAME
	private static final String DATABASE_NAME = "zadatak.db";
	public static final String ID_COLUMN_NAME = "_id";
	private static final int DATABASE_VERSION = 2;
	
	/**************************** DATABASE CONSTRUCTOR ****************************\
	| This is the constructor for the database class, it does not do much and      |
	| most of the work is actually done by the on create function of this class    |
	\******************************************************************************/
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	/********************************** ON CREATE *********************************\
	| This function is run when the application is started it is in charge of      |
	| setting up the databases that will be used for the app. Three databases are  |
	| created for this application. The database of tasks that must be             |
	| accomplished, the database of user defined settings, and the database of     |
	| scheduled tasks used by the scheduler class to persist data without having   |
	| to recalculate it.                                                           |
	\******************************************************************************/	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create Tasks Database
		Attributes[] rows = Task.Attributes.values();
		String databasecreate = "create table " + TASK_TABLE_NAME + "("
				+ ID_COLUMN_NAME +" integer primary key autoincrement";
		for (int i = 0 ; i < rows.length; i++) {
			databasecreate += "," + rows[i].toString() + " text";
		}
		databasecreate += ");";
		db.execSQL(databasecreate);
		
		// Create Setting Database
		db.execSQL("create table " + SETTING_TABLE_NAME +"("+ID_COLUMN_NAME+" integer primary key autoincrement, settingname text, settingvalue text);");
		
		// Create the Schedule database
		db.execSQL("create table " + SCHEDULE_TABLE_NAME +"("+ID_COLUMN_NAME+" integer primary key autoincrement, day text, tasklist text);");
	}
	
	/********************************* ON UPGRADE *********************************\
	| This function is run whenever the application is upgraded. It should         |
	| contain code in order to update the database from version to version.        |
	| However it currently just deletes the database and allows the new version    |
	| to recreate the database                                                     |
	\******************************************************************************/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SETTING_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);
		onCreate(db);
	}
	
}
