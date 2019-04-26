package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cmpe220.smartalertapp.R;

public class ProceedToProfile extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proceed_to_profile);

        final Button nextButton = (Button) findViewById(R.id.letsgo);
        final Intent homeIntent = new Intent(this, HomeScreenActivity.class);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Go to Home Screen");
                startActivity(homeIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
