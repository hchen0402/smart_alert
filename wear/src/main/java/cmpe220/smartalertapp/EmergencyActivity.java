package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class EmergencyActivity extends Activity {

    private RelativeLayout CheckScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency);


        CheckScreen = (RelativeLayout) findViewById(R.id.click_screen);
        CheckScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoadIntent = new Intent(getApplicationContext(), LoadActivity.class);
                startActivity(LoadIntent);
            }
        });
        CheckScreen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent emergencyIntent = new Intent(getApplicationContext(), WatchActivityNew.class);
                startActivity(emergencyIntent);
                return true;
            }
        });



    }
}
