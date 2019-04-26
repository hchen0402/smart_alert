package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class EmergencyOptionsActivity extends Activity {

    private String backScreen;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            backScreen = extras.getString("backscreen");
        }
        else
        {
            backScreen = "home";
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_options);

        final Button medicalInfoButton = (Button) findViewById(R.id.medicalInfoButton);
        final Button allergiesButton = (Button) findViewById(R.id.allergiesButton);
        final Button emergencyContactsButton = (Button) findViewById(R.id.emergencyContactsButton);

        final Intent medicalIntent = new Intent(this, ViewMedicalActivity.class);
        final Intent allergyIntent = new Intent(this, AllergyActivity.class);
        final Intent emergencyContactIntent = new Intent(this, EmergencyContactActivity.class);

        medicalInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (backScreen.equals("emergency")) {
                    medicalIntent.putExtra("backscreen", "emergency");
                    startActivity(medicalIntent);
                } else {
                    startActivity(medicalIntent);
                }
            }
        });

        allergiesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (backScreen.equals("emergency")) {
                    allergyIntent.putExtra("backscreen", "emergency");
                    startActivity(allergyIntent);
                } else {
                    startActivity(allergyIntent);
                }
            }
        });

        emergencyContactsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (backScreen.equals("emergency")) {
                    emergencyContactIntent.putExtra("backscreen", "emergency");
                    startActivity(emergencyContactIntent);
                } else {
                    startActivity(emergencyContactIntent);
                }
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (backScreen.equals("home")) {
                        Intent BackIntent = new Intent(this, WatchActivityNew.class);
                        startActivity(BackIntent);
                    }
                    else if (backScreen.equals("emergency")) {
                        Intent BackIntent = new Intent(this, MessageActivity.class);
                        startActivity(BackIntent);
                    } else {
                        Intent BackIntent = new Intent(this, WatchActivityNew.class);
                        startActivity(BackIntent);
                    }

                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
