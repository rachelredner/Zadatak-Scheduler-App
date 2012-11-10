package org.zadatak.zadatak;

import android.os.Bundle;
import android.os.PowerManager;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class Activity_AlertTask extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_task);
        
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        						  WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | 
        						  WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
        						  WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_alert_task, menu);
        return true;
    }
}
