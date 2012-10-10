package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NewTask extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        
        
        Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() { public void onClick(View v) { saveTask();} } );
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_task, menu);
        return true;
    }
    
    private void saveTask(){
    	finish();
    }
}
