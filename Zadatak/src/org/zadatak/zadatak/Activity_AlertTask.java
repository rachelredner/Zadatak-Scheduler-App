package org.zadatak.zadatak;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Activity_AlertTask extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_task);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
        						  WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | 
        						  WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
        						  WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        
        //
        Button beginTask = (Button)findViewById(R.id.beginTask);
        beginTask.setOnClickListener(new OnClickListener(){ public void onClick(View v){ beginTask(); } } );
		
		Button postponeTask = (Button)findViewById(R.id.postponeTask);
		postponeTask.setOnClickListener(new OnClickListener(){ public void onClick(View v){ postponeTask(); } } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_alert_task, menu);
        return true;
    }
    
    public void beginTask() {
    	// Quit the activity
    	finish();
    }
    public void postponeTask() {
    	MediaPlayer mp = MediaPlayer.create(Activity_AlertTask.this, R.raw.damdadido);
    	mp.setVolume(100, 100);
        mp.setOnCompletionListener(new OnCompletionListener() {
            //@Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }

        });   
        mp.start();
        //finish();
    }
    public void cancelTask() {
    	// Quit the activity
    	finish();
    }
}
