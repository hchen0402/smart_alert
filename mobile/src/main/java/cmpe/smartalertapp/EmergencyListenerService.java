package cmpe.smartalertapp;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class EmergencyListenerService extends WearableListenerService {

    private Intent EmergencyIntent;

    private static final String RECEIVER_SERVICE_PATH = "emergency_receiver";

    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i("Message received", "c");
        EmergencyIntent = new Intent(getApplicationContext(), EmergencyResponseActivity.class);
        EmergencyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //EmergencyResponseActivity.contactsMessaged = false;
        startActivity(EmergencyIntent);
    }
}
