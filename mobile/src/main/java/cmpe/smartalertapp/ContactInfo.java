package cmpe.smartalertapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import cmpe220.smartalertapp.R;

public class ContactInfo extends Activity {
    private GoogleApiClient mGoogleApiClient;
    private String[] contactInfo;
    private AlertDialog.Builder dialogBuilder;
    private String viewMode;
    private int contactOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            viewMode = extras.getString("view");
            contactOrder = extras.getInt("order");
        }
        else
        {
            viewMode = "Add";
        }

        final Button saveButton = (Button) findViewById(R.id.saveButton);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        final Intent EmergencyContant = new Intent(this, EmergencyContactsActivity.class);

        EditText nameEntry = (EditText)findViewById(R.id.nameEntry);
        EditText relationshipEntry = (EditText)findViewById(R.id.relationshipEntry);
        EditText phoneEntry = (EditText)findViewById(R.id.phoneEntry);
        String name = nameEntry.getText().toString();
        final String relationship = relationshipEntry.getText().toString();
        String phone = phoneEntry.getText().toString();

//        if (name == null || relationship == null || phone == null) {
//            Context context = getApplicationContext();
//            CharSequence text = "Missing information!";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//        }

        if (viewMode.equals("Edit")){
            String[] ContactsCopy = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
            nameEntry.setText(ContactsCopy[contactOrder]);
            relationshipEntry.setText(ContactsCopy[contactOrder+1]);
            phoneEntry.setText(ContactsCopy[contactOrder+2]);
            setTitle("Edit Contact");
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(EmergencyContant);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CharSequence text = null;
                final EditText nameEntry = (EditText)findViewById(R.id.nameEntry);
                final EditText relationshipEntry = (EditText)findViewById(R.id.relationshipEntry);
                final EditText phoneEntry = (EditText)findViewById(R.id.phoneEntry);
                Log.d("name", nameEntry.getText().toString());
                if (nameEntry.getText().toString().trim().length() == 0 || relationshipEntry.getText().toString().trim().length() == 0
                        || phoneEntry.getText().toString().trim().length() == 0 ) {
//                    if (nameEntry.getText().toString().trim().length() == 0 )
//                        text = "Missing Name!";
//                    else if (relationshipEntry.getText().toString().trim().length() == 0 )
//                        text = "Missing Relationship!";
//                    else
//                        text = "Missing Phone Number!";
//                    Context context = getApplicationContext();
//                    int duration = Toast.LENGTH_SHORT;
//                    Toast toast = Toast.makeText(context, text, duration);
//                    toast.show();
                    dialogShowing();
                } else if (viewMode.equals("Edit")){
                    contactInfo = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
                    contactInfo[contactOrder] = nameEntry.getText().toString();
                    contactInfo[contactOrder+1] = relationshipEntry.getText().toString();
                    contactInfo[contactOrder+2] = phoneEntry.getText().toString();
                    MainActivity.contactInfoDataBase.put(MainActivity.currentUserName, contactInfo);

                    mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                            .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/emergencycontact");
                                    putDataMapReq.getDataMap().putStringArray("emergencycontact", contactInfo);
                                    PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);

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
                    mGoogleApiClient.connect();
                    startActivity(EmergencyContant);
                } else {
                    Log.i("Point1: ", "On Click");
                    if (MainActivity.contactInfoDataBase.containsKey(MainActivity.currentUserName)) {
                        contactInfo = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
                        String[] newInfo = new String[contactInfo.length+3];
                        for (int i = 0; i < contactInfo.length; i++) {
                            newInfo[i] = contactInfo[i];
                        }
                        newInfo[contactInfo.length] = nameEntry.getText().toString();
                        newInfo[contactInfo.length+1] = relationshipEntry.getText().toString();
                        newInfo[contactInfo.length+2] = phoneEntry.getText().toString();
                        MainActivity.contactInfoDataBase.put(MainActivity.currentUserName, newInfo);
                        contactInfo = newInfo;
                    } else {
                        contactInfo = new String[]{nameEntry.getText().toString(),
                                relationshipEntry.getText().toString(), phoneEntry.getText().toString()};

                        MainActivity.contactInfoDataBase.put(MainActivity.currentUserName, contactInfo);
                    }
                    mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                            .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/emergencycontact");
                                    putDataMapReq.getDataMap().putStringArray("emergencycontact", contactInfo);
                                    PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);

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
                    startActivity(EmergencyContant);
                }
            }
        });

    }

    private void dialogShowing() {
        dialogBuilder = new AlertDialog.Builder(ContactInfo.this, R.style.CustomDialog);
        dialogBuilder.setTitle("Alert");
        dialogBuilder.setMessage("Some fields are missing, do you wish to save anyway?");;
        dialogBuilder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText nameEntry = (EditText) findViewById(R.id.nameEntry);
                        final EditText relationshipEntry = (EditText) findViewById(R.id.relationshipEntry);
                        final EditText phoneEntry = (EditText) findViewById(R.id.phoneEntry);
                        Log.i("Point1: ", "On Click");
                        if (viewMode.equals("Edit")) {
                            contactInfo = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
                            contactInfo[contactOrder] = nameEntry.getText().toString();
                            contactInfo[contactOrder + 1] = relationshipEntry.getText().toString();
                            contactInfo[contactOrder + 2] = phoneEntry.getText().toString();
                            MainActivity.contactInfoDataBase.put(MainActivity.currentUserName, contactInfo);
                        } else if (MainActivity.contactInfoDataBase.containsKey(MainActivity.currentUserName)) {
                            contactInfo = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
                            String[] newInfo = new String[contactInfo.length + 3];
                            for (int i = 0; i < contactInfo.length; i++) {
                                newInfo[i] = contactInfo[i];
                            }
                            newInfo[contactInfo.length] = nameEntry.getText().toString();
                            newInfo[contactInfo.length + 1] = relationshipEntry.getText().toString();
                            newInfo[contactInfo.length + 2] = phoneEntry.getText().toString();
                            MainActivity.contactInfoDataBase.put(MainActivity.currentUserName, newInfo);
                        } else {
                            contactInfo = new String[]{nameEntry.getText().toString(),
                                    relationshipEntry.getText().toString(), phoneEntry.getText().toString()};

                            MainActivity.contactInfoDataBase.put(MainActivity.currentUserName, contactInfo);
                        }
                        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                    @Override
                                    public void onConnected(Bundle bundle) {
                                        Log.i("Point4: ", "Client Connected");
                                        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/emergencycontact");

                                        putDataMapReq.getDataMap().putStringArray("emergencycontact", contactInfo);

                                        Log.i("Name: ", nameEntry.getText().toString());
                                        Log.i("Relationship: ", relationshipEntry.getText().toString());
                                        Log.i("Phone: ", phoneEntry.getText().toString());
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
                        Intent EmergencyContact = new Intent(getApplicationContext(), EmergencyContactsActivity.class);
                        startActivity(EmergencyContact);
                    }
                }

        );

            dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener()

                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }

            );

            AlertDialog alert = dialogBuilder.create();
            alert.show();
        }

        @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), EmergencyContactsActivity.class);
        startActivity(BackIntent);
    }
}
