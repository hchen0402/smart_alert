package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;


public class WatchActivityNew extends Activity {

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;

    private boolean shakeOn;
    private boolean pulseOn;
    private Intent SensorServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlay.setIntroText("Press and hold to exit app");
        mDismissOverlay.showIntroIfNecessary();

        mDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent ev) {
                mDismissOverlay.show();
            }

        });

        /*
        shakeOn = false;
        pulseOn = false;

        if (UserData.containsKey("shakeOn")){
            shakeOn = (boolean) UserData.get("shakeOn");
        } else {
            UserData.put("shakeOn", false);
        }

        if (UserData.containsKey("pulseOn")){
            pulseOn = (boolean) UserData.get("pulseOn");
        } else {
            UserData.put("pulseOn", false);
        }

        if (shakeOn){
            SensorServiceIntent = new Intent(getApplicationContext(), ShakeMonitorService.class);
            startService(SensorServiceIntent);
        }

        if (pulseOn){
            SensorServiceIntent = new Intent(getApplicationContext(), PulseMonitorService.class);
            startService(SensorServiceIntent);
        }
        */

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
                    if (x2 > x1) {
                        Intent emergencyIntent = new Intent(this, EmergencyActivity.class);
                        startActivity(emergencyIntent);
                    }
                    else {
                        Intent viewMedicalIntent = new Intent(this, EmergencyOptionsActivity.class);
                        viewMedicalIntent.putExtra("backscreen", "home");
                        startActivity(viewMedicalIntent);
                    }

                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return mDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return mDetector.onTouchEvent(event) || super.onTouchEvent(event);
//    }

}
