package Test.Buttons;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TestButtons extends Activity  {

	



		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_test_buttons);
			
			//create a button class to represent the mega button
			Button button = (Button)findViewById(R.id.mega);
			//create a listener for regular clicks
			button.setOnClickListener(new OnClickListener(){
				//if clicked
				public void onClick(View v){
					//grab the text box defined in XML as id9
					TextView text = (TextView)findViewById(R.id.id9);
					//if the text says that the button was already pressed
					if (text.getText().toString().equals("Mega Button pressed!")) {
						//change the text
						text.setText("Mega Button already pressed");
					}
					//clear the text if the button has been pushed twice
					else if (text.getText().toString().equals("Mega Button already pressed")){
						text.setText(" ");
					}
					//Mega button pressed for the first time
					else
						text.setText("Mega Button pressed!");	
				}
			});
			
			//Button class for the MiniButton (called ok in the XML)
			//On click, grab the text from the text box and output it on screen
			Button button3 = (Button)findViewById(R.id.ok);
			button3.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					//grab the text from the text box,called editor in XML
					EditText mEdit   = (EditText)findViewById(R.id.editor);
					//create a string with the grabbed text
					String string1 = mEdit.getText().toString();
					//change the text in id8 to the captured text
					TextView text = (TextView)findViewById(R.id.id8);
					text.setText(string1);	
					//clear the text box
					mEdit.setText("");
				}
			});
					
			
			//on long click of the mega button
				button.setOnLongClickListener(new OnLongClickListener() {
				public boolean onLongClick(View arg0) {
					TextView text = (TextView)findViewById(R.id.id9);
					text.setText("MEGA BUTTON HELD \n OH SO GENTLY ");	
					return true;
				}
				
			});
			
			//allows button mini to be a context menu on long click
			Button contextbutton = (Button)findViewById(R.id.ok);
			registerForContextMenu(contextbutton);
		}
		//automatically created by eclipse
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.activity_test_buttons, menu);
			return true;
		}
		
		@Override
		//build a context menu that will be called on long click of the mii button
		public void onCreateContextMenu (ContextMenu menu, View view, ContextMenuInfo menuInfo)
		{
			super.onCreateContextMenu(menu, view, menuInfo);
			menu.setHeaderTitle("An awesome menu");
			menu.add(0, view.getId(), 0, "Choice 1");
			menu.add(0, view.getId(), 0, "Choice 2");
			
		}
		
		//depending on which of the context menu is clicked, call the appropriate function bellow
		public boolean onContextItemSelected (MenuItem item)
		{
			if (item.getTitle() == "Choice 1")
			{
				contextFunction1(item.getItemId());
			}
			else if (item.getTitle() == "Choice 2")
			{
				contextFunction2(item.getItemId());
			}
			else
				return false;
			return true;
		}
		
		//Create a toast
		public void contextFunction1 (int id)
		{
			Toast.makeText(this, "You chose option 1", Toast.LENGTH_LONG).show();
		}
		
		public void contextFunction2 (int id)
		{
			Toast.makeText(this, "You chose option 2", Toast.LENGTH_LONG).show();
		}
			
		//just an experiment, this code reacts to the menu key on the phone's keyboard being pressed
		public boolean onKeyDown(int keyCode, KeyEvent event)
		{
			//if a key is pressed, compare to this key code
			if (keyCode == KeyEvent.KEYCODE_MENU)
			{
				//change the text in ID8 appropriately
				TextView text = (TextView)findViewById(R.id.id8);
				text.setText ("Menu Key Pressed!");
				//if this function returned false, then the code would continue to find a function to handle the button press
				return true;
			}
			return false;
		}
	
	


	
}