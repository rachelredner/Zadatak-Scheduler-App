package Test.Buttons;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;


public class TestButtons extends Activity implements OnClickListener, OnLongClickListener {

	



		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_test_buttons);
			
			Button button = (Button)findViewById(R.id.mega);
			button.setOnClickListener(this);
			button.setOnLongClickListener(this);
			Button contextbutton = (Button)findViewById(R.id.ok);
			registerForContextMenu(contextbutton);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.activity_test_buttons, menu);
			return true;
		}
		
		@Override
		public void onCreateContextMenu (ContextMenu menu, View view, ContextMenuInfo menuInfo)
		{
			super.onCreateContextMenu(menu, view, menuInfo);
			menu.setHeaderTitle("An awesome menu");
			menu.add(0, view.getId(), 0, "Choice 1");
			menu.add(0, view.getId(), 0, "Choice 2");
			
		}
		
		
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
		
		
		public void contextFunction1 (int id)
		{
			Toast.makeText(this, "You chose option 1", Toast.LENGTH_LONG).show();
		}
		
		public void contextFunction2 (int id)
		{
			Toast.makeText(this, "You chose option 2", Toast.LENGTH_LONG).show();
		}
			
		
		public boolean onKeyDown(int keyCode, KeyEvent event)
		{
			if (keyCode == KeyEvent.KEYCODE_MENU)
			{
				TextView text = (TextView)findViewById(R.id.id8);
				text.setText ("Menu Key Pressed!");
				return true;
			}
			return false;
		}
	
	

	public void onClick(View v) {
		TextView text = (TextView)findViewById(R.id.id9);
		if (text.getText().toString().equals("MEGA BUTTON PRESSED!"))
		{
			text.setText(" ");
		}
		else
			text.setText("MEGA BUTTON PRESSED!");
		
	}

	public boolean onLongClick(View arg0) {
		TextView text = (TextView)findViewById(R.id.id9);
		text.setText("MEGA BUTTON HELD \n OH SO GENTLY ");	
		return true;
	}
}