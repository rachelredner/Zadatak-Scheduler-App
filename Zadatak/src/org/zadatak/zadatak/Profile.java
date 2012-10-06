package org.zadatak.zadatak;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Profile extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }
}
