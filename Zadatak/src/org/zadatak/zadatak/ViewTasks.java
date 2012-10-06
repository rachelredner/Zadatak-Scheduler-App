package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;

public class ViewTasks extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_tasks, menu);
        return true;
    }
}
