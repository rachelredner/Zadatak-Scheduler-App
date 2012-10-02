package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NewTask extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_task, menu);
        return true;
    }
}
