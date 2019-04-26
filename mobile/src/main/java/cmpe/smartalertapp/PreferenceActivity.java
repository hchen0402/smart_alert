package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

import cmpe220.smartalertapp.R;

public class PreferenceActivity extends Activity {

    private GoogleApiClient mGoogleApiClient;
    private Intent profileOption;
    private CheckBox textContactsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        CheckBox pulseMonitorCheckBox= (CheckBox) findViewById( R.id.pulseMonitor);
        CheckBox gpsCheckBox= (CheckBox) findViewById( R.id.gps);
        CheckBox alarmCheckBox= (CheckBox) findViewById( R.id.alarm);
        CheckBox authoritiesCheckBox= (CheckBox) findViewById( R.id.authorities);
        profileOption = new Intent(this, ProfileOption.class);
        Button saveButton = (Button)findViewById(R.id.saveButton);
        final EditText text = (EditText)findViewById( R.id.enterTextMessage);
        textContactsCheckBox = (CheckBox) findViewById(R.id.emergencyContacts);
        text.setEnabled(false);
        textContactsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    text.setEnabled(true);
                } else {
                    text.setEnabled(false);
                }
            }
        });

        if (MainActivity.preferenceDataBase.containsKey(MainActivity.currentUserName)) {
            String[] preferences = MainActivity.preferenceDataBase.get(MainActivity.currentUserName);
            for (int i = 0; i < preferences.length; i++) {
                if (preferences[i] != "f") {
                    switch (i) {
                        case 0:
                            pulseMonitorCheckBox.setChecked(true);
                            break;
                        case 1:
                            gpsCheckBox.setChecked(true);
                            break;
                        case 2:
                            alarmCheckBox.setChecked(true);
                            break;
                        case 3:
                            authoritiesCheckBox.setChecked(true);
                            break;
                        case 4:
                            textContactsCheckBox.setChecked(true);
                            text.setText(preferences[i]);
                            break;
                    }
                }
            }
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox pulseMonitorCheckBox= (CheckBox) findViewById( R.id.pulseMonitor);
                CheckBox gpsCheckBox= (CheckBox) findViewById( R.id.gps);
                CheckBox alarmCheckBox= (CheckBox) findViewById( R.id.alarm);
                CheckBox authoritiesCheckBox= (CheckBox) findViewById( R.id.authorities);

                EditText text = (EditText)findViewById( R.id.enterTextMessage);

                int count = 0;
                if (pulseMonitorCheckBox.isChecked()) count++;
                if (gpsCheckBox.isChecked()) count++;
                if (alarmCheckBox.isChecked()) count++;
                if (authoritiesCheckBox.isChecked()) count++;
                if (textContactsCheckBox.isChecked()) count++;

                ArrayList<String> preferenceList = new ArrayList<String>();
                if (pulseMonitorCheckBox.isChecked()) {
                    preferenceList.add("t");
                } else {
                    preferenceList.add("f");
                }
                if (gpsCheckBox.isChecked()) {
                    preferenceList.add("t");
                } else {
                    preferenceList.add("f");
                }
                if (alarmCheckBox.isChecked()) {
                    preferenceList.add("t");
                    EmergencyResponseActivity.alarmOn = true;
                } else {
                    preferenceList.add("f");
                    EmergencyResponseActivity.alarmOn = false;
                }
                if (authoritiesCheckBox.isChecked()) {
                    preferenceList.add("t");
                    EmergencyResponseActivity.callForHelp = true;
                } else {
                    preferenceList.add("f");
                    EmergencyResponseActivity.callForHelp = false;
                }
                if (textContactsCheckBox.isChecked()) {
                    preferenceList.add(text.getText().toString());
                    EmergencyResponseActivity.messageContacts = true;
                    EmergencyResponseActivity.message = text.getText().toString();
                } else {
                    preferenceList.add("f");
                    EmergencyResponseActivity.messageContacts = false;
                }
                final String[] preferenceStrings = new String[5];
                for (int i = 0; i < 5; i++) {
                    preferenceStrings[i] = preferenceList.get(i);
                }

                MainActivity.preferenceDataBase.put(MainActivity.currentUserName, preferenceStrings);
                mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i("Point4: ", "Client Connected");
                                PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/preferences");
                                putDataMapReq.getDataMap().putStringArray("preferences", preferenceStrings);
                                PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                                Log.i("Point5: ", "Pre-putDataItem");
                                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
                                Log.i("Point6: ", "Post-putDataItem");

                                //mGoogleApiClient.disconnect();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                            }
                        })
                        .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(ConnectionResult connectionResult) {
                                Log.i("Connection: ", "Failed");
                            }
                        })
                        .addApi(Wearable.API)
                        .build();
                Log.i("Point2: ", "Build Client");
                mGoogleApiClient.connect();
                Log.i("Point3: ", "Connect Client");
                startActivity(profileOption);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), ProfileOption.class);
        startActivity(BackIntent);
    }
}