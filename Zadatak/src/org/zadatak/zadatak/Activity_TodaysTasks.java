package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Activity_TodaysTasks extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_tasks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_todays_tasks, menu);
        return true;
    }
}
