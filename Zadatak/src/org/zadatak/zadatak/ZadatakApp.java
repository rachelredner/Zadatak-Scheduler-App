package org.zadatak.zadatak;

import org.zadatak.zadatak.Task.Attributes;

import android.app.Application;
import android.widget.CheckBox;
import android.widget.Toast;

public class ZadatakApp extends Application {
	public String state;
	public DatabaseManager dbman = new DatabaseManager(this);
	
	public void toaster(String string) {
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(this, string, duration);
		toast.show();
	}
	
	
	
	Setting setting = new Setting();
	public class Setting {
		
		public void set(Settings setting,String value) {
			dbman.setSetting(setting.toString(), value);
		}
		public String get (Settings setting) {
			return dbman.getSetting(setting.toString());
		}
		
		///////////// Save checkbox function ported from Task.java
		// TODO change comment to match 'Setting' instead of 'Task'
		/******************************** SET CHECKBOX ********************************\
		| This function sets the attribute equal to the value of the checkbox, either  |
		| checked or unchecked.                                                        |
		\******************************************************************************/
		public void saveCheckbox (Settings setting, CheckBox checkBox){
			
			set(setting,checkBox.isChecked() ? "TRUE" : "FALSE");
		}
		
		/******************************** GET CHECKBOX ********************************\
		| This function gets the attribute requested and sets the checkbox equal to    |
		| it's value, either checked or unchecked                                      |
		\******************************************************************************/
		public void loadCheckbox (Settings setting, CheckBox checkBox){
			boolean checked = get(setting).equals("TRUE");
			checkBox.setChecked(checked);
		}
	}
}
enum Settings {
	StartTime,
	EndTime,
	UseSchedule,
	SchedulingSchema
}
