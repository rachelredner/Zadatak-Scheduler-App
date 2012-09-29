package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Zadatak extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zadatak);
        
        /*
        Button button = (Button)findViewById(R.id.mega);
		button.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				TextView text = (TextView)findViewById(R.id.id9);
				if (text.getText().toString().equals("Mega Button pressed!")) {
					text.setText("Mega Button already pressed");
				}
				else if (text.getText().toString().equals("Mega Button already pressed")){
					text.setText(" ");
				}
				else
					text.setText("Mega Button pressed!");	
			}
		});
		
		Button archive
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_zadatak, menu);
        return true;
    }
}
