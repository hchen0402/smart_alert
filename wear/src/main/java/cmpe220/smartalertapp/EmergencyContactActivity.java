package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class EmergencyContactActivity extends SwipeActivity {

    private String backScreen;
    TextView contactsText;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contacts);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            backScreen = extras.getString("backscreen");
        }
        else
        {
            backScreen = "home";
        }
    }

    @Override
    protected void onStart() {
        if (!WatchActivity.UserData.containsKey("contacts")){
            WatchActivity.UserData.put(
                    "contacts",
                    new String[] {""});
        }

        HashMap UserDataCopy = WatchActivity.UserData;

        String[] contactsDataArray = (String[]) UserDataCopy.get("contacts");
        contactsText = (TextView) findViewById(R.id.emergency_contact_data);

        if (contactsDataArray != null){
            if (contactsDataArray.length == 1) {
                contactsText.setText("Contacts not found");
            } else {
                String text = "";
                for (int i = 0; i < contactsDataArray.length; i += 3) {
                    if (i == 0) {
                        text = "Name: " + contactsDataArray[i] + "\n" +
                                "Relationship: " + contactsDataArray[i + 1] + "\n" +
                                "Phone: " + contactsDataArray[i + 2] + "\n";
                    } else {
                        text = text + "\n" + "Name: " + contactsDataArray[i] + "\n" +
                                "Relationship: " + contactsDataArray[i + 1] + "\n" +
                                "Phone: " + contactsDataArray[i + 2] + "\n";
                    }
                    if (i + 3 == contactsDataArray.length) {
                        text = text + "\n\n";
                    }
                }
                contactsText.setText(text);
            }
        }

        super.onStart();
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
                    Intent emergencyIntent = new Intent(this, EmergencyOptionsActivity.class);
                    if (backScreen.equals("emergency")) {
                        emergencyIntent.putExtra("backscreen", "emergency");
                        startActivity(emergencyIntent);
                    } else {
                        startActivity(emergencyIntent);
                    }
                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void previous() {
        Intent emergencyIntent = new Intent(this, EmergencyOptionsActivity.class);
        if (backScreen.equals("emergency")) {
            emergencyIntent.putExtra("backscreen", "emergency");
            startActivity(emergencyIntent);
        } else {
            startActivity(emergencyIntent);
        }
    }

    @Override
    protected void next() {
        Intent emergencyIntent = new Intent(this, EmergencyOptionsActivity.class);
        if (backScreen.equals("emergency")) {
            emergencyIntent.putExtra("backscreen", "emergency");
            startActivity(emergencyIntent);
        } else {
            startActivity(emergencyIntent);
        }
    }

}
