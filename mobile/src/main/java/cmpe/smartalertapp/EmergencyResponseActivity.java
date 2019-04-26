package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cmpe220.smartalertapp.R;


public class EmergencyResponseActivity extends Activity {

    private Button CallEmergencyServices;
    private Button EmergencyContacts;
    private Button LocateHospital;
    private Button ViewProfile;
    private Button Alarm;
    static public boolean alarmOn;
    private boolean alarmOnCopy;
    static private boolean alarmed;
    private Button Dismiss;
    private Intent UserIntent;
    static public boolean messageContacts;
    static private boolean contactsMessaged;
    static public String message;
    static public boolean callForHelp;
    static private boolean called;
    private boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_screen);
        loggedIn = MainActivity.loggedIn;

        if (loggedIn && callForHelp && !called){
            alarmOn = false;
            called = true;
            Toast.makeText(getApplicationContext(), "Calling emergency services", Toast.LENGTH_SHORT).show();
            UserIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4082121211")); // change to local emergency number
            startActivity(UserIntent);
        }

        CallEmergencyServices = (Button) findViewById(R.id.callButton);
        CallEmergencyServices.setText("Call Emergency Services");
        CallEmergencyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4082121211")); // change to local emergency number
                startActivity(UserIntent);
            }
        });

        if (loggedIn && messageContacts && !contactsMessaged){
            SmsManager sm = SmsManager.getDefault();
            String msg = message;
            String [] ContactsCopy = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
            if (ContactsCopy != null && ContactsCopy.length > 0) {
                for (int i = 0; i < ContactsCopy.length; i += 3) {
                    String phoneOld = ContactsCopy[i + 2];
                    String phoneNew = "";
                    for (int j = 0; j < phoneOld.length(); j++) {
                        if (Character.isDigit(phoneOld.charAt(j))) {
                            phoneNew = phoneNew + phoneOld.charAt(j);
                        }
                    }
                    if (phoneNew.length() >= 9) {
                        try {
                            sm.sendTextMessage(phoneNew, null, msg, null, null);
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Could not message "+ContactsCopy[i]+"\n"+
                                                                    "Invalid number: "+phoneNew, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                Toast.makeText(getApplicationContext(), "Contacts have been messaged", Toast.LENGTH_SHORT).show();
            }
            contactsMessaged = true;
        }
        EmergencyContacts = (Button) findViewById(R.id.emergencyContactsButton);
        EmergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedIn){
                    UserIntent = new Intent(getApplicationContext(), CallContactsActivity.class);
                    startActivity(UserIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Log in to view contacts", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LocateHospital = (Button) findViewById(R.id.hospitalLocatorButton);
        LocateHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=hospitals");
                UserIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                UserIntent.setPackage("com.google.android.apps.maps");
                if (UserIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(UserIntent);
                } else {
                    UserIntent = new Intent(Intent.ACTION_VIEW);
                    UserIntent.setData(Uri.parse("geo:0,0?q=hospitals"));
                    if (UserIntent.resolveActivity(getPackageManager()) != null){
                        startActivity(UserIntent);
                    } else {
                        Toast.makeText(getParent(), "No available map application", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ViewProfile = (Button) findViewById(R.id.viewProfileButton);
        ViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedIn){
                    UserIntent = new Intent(getApplicationContext(), PersonalDataActivity.class); //change class to profile
                    UserIntent.putExtra("view", "Read only");
                    startActivity(UserIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Log in to view profile", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Alarm = (Button) findViewById(R.id.stopAlarmButton);
        if (loggedIn && alarmOn && !alarmed){
            alarmed = true;
            UserIntent = new Intent(getApplicationContext(), AlarmService.class);
            startService(UserIntent);
            Alarm.setText("Stop\nAlarm");
        } else {
            Alarm.setText("Sound\nAlarm");
        }
        alarmOnCopy = alarmOn;
        Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserIntent = new Intent(getApplicationContext(), AlarmService.class);
                if (alarmOnCopy) {
                    Alarm.setText("Sound\nAlarm");
                    stopService(UserIntent);
                } else {
                    Alarm.setText("Stop\nAlarm");
                    startService(UserIntent);
                }
                alarmOnCopy = !alarmOnCopy;
            }
        });

        Dismiss = (Button) findViewById(R.id.dismissButton);
        Dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loggedIn){
                    called = false;
                    alarmed = false;
                    contactsMessaged = false;
                    Intent AlarmIntent = new Intent(getApplicationContext(), AlarmService.class);
                    stopService(AlarmIntent);
                    UserIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                    startActivity(UserIntent);
                } else {
                    UserIntent = new Intent(getApplicationContext(), LoginPage.class);
                    startActivity(UserIntent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(BackIntent);
    }

    /*
    @Override
    protected void onStart() {
        if (callFinished) {
            alarmOn = alarmSaved;
            Alarm = (Button) findViewById(R.id.stopAlarmButton);
            if (alarmOn){
                UserIntent = new Intent(getApplicationContext(), AlarmService.class);
                startService(UserIntent);
                Alarm.setText("Stop\nAlarm");
            } else {
                Alarm.setText("Sound\nAlarm");
            }
        }
        super.onStart();
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emergency_response, menu);
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
