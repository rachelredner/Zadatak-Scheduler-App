package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Zadatak extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadatak);
        
        // Grab the new task button and asign a click listener to load the
        // new task activity when clicked
		Button newtaskButton = (Button)findViewById(R.id.newtask);
		newtaskButton.setOnClickListener(new OnClickListener(){ public void onClick(View v){ goToNewTask(); } } );
		
		Button tasklistButton = (Button) findViewById(R.id.tasklist);
		tasklistButton.setOnClickListener(new OnClickListener() { public void onClick(View v) { goToTaskList();} } );
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
    	Context context = getApplicationContext();
    	CharSequence text = "GO TO TASK LIST";
    	int duration = Toast.LENGTH_SHORT;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    	/*
    	Intent intent = new Intent(Zadatak.this, NewTask.class);
		Zadatak.this.startActivity(intent);
		*/
    }
}
