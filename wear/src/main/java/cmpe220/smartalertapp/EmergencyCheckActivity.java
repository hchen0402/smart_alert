package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


public class EmergencyCheckActivity extends Activity {

    private RelativeLayout CheckScreen;
    private Intent EmergencyIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_check);
        CheckScreen = (RelativeLayout) findViewById(R.id.emergency_check);
        CheckScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmergencyIntent = new Intent(getApplicationContext(), LoadActivity.class);
                startActivity(EmergencyIntent);
            }
        });
        CheckScreen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EmergencyIntent = new Intent(getApplicationContext(), WatchActivity.class);
                startActivity(EmergencyIntent);
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emergency_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
