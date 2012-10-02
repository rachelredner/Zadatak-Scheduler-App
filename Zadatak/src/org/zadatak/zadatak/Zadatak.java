package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Zadatak extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadatak);
        
        // Grab the new task button and asign a click listener to load the
        // new task activity when clicked
		Button button = (Button)findViewById(R.id.newtask);
		button.setOnClickListener(new OnClickListener(){ public void onClick(View v){ goToNewTask(); } });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_zadatak, menu);
        return true;
    }
    
    
    private void goToNewTask(){
    	Intent intent = new Intent(Zadatak.this, NewTask.class);
		Zadatak.this.startActivity(intent);
    }
    
    private void goToTaskList(){
    	Intent intent = new Intent(Zadatak.this, NewTask.class);
		Zadatak.this.startActivity(intent);
    }
}
