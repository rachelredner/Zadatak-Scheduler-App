package org.zadatak.zadatak;

import android.app.Application;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TimePicker;
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
			String fromString = get(setting);
			if (fromString == null) return;
			boolean checked = fromString.equals("TRUE");
			checkBox.setChecked(checked);
		}
		
		
		public void saveTimePicker (Settings setting, TimePicker timePicker) {
			String toString = "";
			Integer currentHour = timePicker.getCurrentHour();
			Integer currentMinute = timePicker.getCurrentMinute();
			toString = currentHour + ":" + currentMinute;
			set (setting, toString);
		}
		
		public void loadTimePicker (Settings setting, TimePicker timePicker) {
			String fromString = get(setting);
			if (fromString == null) return;
			String[] split = fromString.split(":");
			Integer currentHour = Integer.parseInt(split[0]);
			Integer currentMinute = Integer.parseInt(split[1]);
			timePicker.setCurrentHour(currentHour);
			timePicker.setCurrentMinute(currentMinute);
		}
		
		public void saveRadioGroup(Settings setting, RadioGroup radioGroup) {
			int radioButton = radioGroup.getCheckedRadioButtonId();
			String toString = "" + radioButton;
			set(setting,toString);
		}
		
		public void loadRadioGroup(Settings setting, RadioGroup radioGroup) {
			String fromString = get (setting);
			if (fromString == null) return;
			int radioButtonId = Integer.parseInt(fromString);
			radioGroup.check(radioButtonId);
		}
	}
}
enum Settings {
	StartTime,
	EndTime,
	UseSchedule,
	SchedulingSchema
}
