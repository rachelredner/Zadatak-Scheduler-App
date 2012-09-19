package Test.Buttons;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TestButtons extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_buttons);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_test_buttons, menu);
        return true;
    }
}
