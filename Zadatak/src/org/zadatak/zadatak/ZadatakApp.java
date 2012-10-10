package org.zadatak.zadatak;

import android.app.Application;
import android.widget.Toast;

public class ZadatakApp extends Application {
	public String state;
	public DatabaseManager dbman = new DatabaseManager(this);
	
	public void toaster(String string) {
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(this, string, duration);
		toast.show();
	}
}
