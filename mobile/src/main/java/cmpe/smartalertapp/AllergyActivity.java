package cmpe.smartalertapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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


public class AllergyActivity extends Activity {
    private GoogleApiClient mGoogleApiClient;
    static String other = null;
    private AlertDialog.Builder dialogBuilder;
    private String[] allergies;
    private Intent profileOption;
    private EditText text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allergy_selection);
        text = (EditText)findViewById( R.id.otherText);
        final CheckBox otherCheckBox= (CheckBox) findViewById( R.id.other);
        profileOption = new Intent(this, ProfileOption.class);
        CheckBox cowsmilkCheckBox= (CheckBox) findViewById( R.id.cowsmilk);
        CheckBox eggsCheckBox= (CheckBox) findViewById( R.id.eggs);
        CheckBox peanutsCheckBox= (CheckBox) findViewById( R.id.peanuts);
        CheckBox fishCheckBox= (CheckBox) findViewById( R.id.fish);
        CheckBox shellfishCheckBox= (CheckBox) findViewById( R.id.shellfish);
        CheckBox treenutsCheckBox= (CheckBox) findViewById( R.id.treenuts);
        CheckBox wheatCheckBox= (CheckBox) findViewById( R.id.wheat);
        CheckBox soyCheckBox= (CheckBox) findViewById( R.id.soy);


        text.setEnabled(false);
        Button continueButton = (Button) findViewById(R.id.continueButton);

        otherCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    text.setEnabled(true);
                } else {
                    text.setEnabled(false);
                }
            }
        });

        if (MainActivity.allergyDataBase.containsKey(MainActivity.currentUserName)) {
            String[] allergyItem = MainActivity.allergyDataBase.get(MainActivity.currentUserName);
            for (int i = 0; i < allergyItem.length; i++) {
                switch (allergyItem[i]) {
                    case "Cow's Milk":
                        cowsmilkCheckBox.setChecked(true);
                        break;
                    case "Eggs":
                        eggsCheckBox.setChecked(true);
                        break;
                    case "Peanuts":
                        peanutsCheckBox.setChecked(true);
                        break;
                    case "Fish":
                        fishCheckBox.setChecked(true);
                        break;
                    case "Shellfish":
                        shellfishCheckBox.setChecked(true);
                        break;
                    case "Tree Nuts":
                        treenutsCheckBox.setChecked(true);
                        break;
                    case "Wheat":
                        wheatCheckBox.setChecked(true);
                        break;
                    case "Soy":
                        soyCheckBox.setChecked(true);
                        break;
                    default:
                        otherCheckBox.setChecked(true);
                        text.setText(allergyItem[i]);
                        break;
                }
            }
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cowsmilkCheckBox= (CheckBox) findViewById( R.id.cowsmilk);
                CheckBox eggsCheckBox= (CheckBox) findViewById( R.id.eggs);
                CheckBox peanutsCheckBox= (CheckBox) findViewById( R.id.peanuts);
                CheckBox fishCheckBox= (CheckBox) findViewById( R.id.fish);
                CheckBox shellfishCheckBox= (CheckBox) findViewById( R.id.shellfish);
                CheckBox treenutsCheckBox= (CheckBox) findViewById( R.id.treenuts);
                CheckBox wheatCheckBox= (CheckBox) findViewById( R.id.wheat);
                CheckBox soyCheckBox= (CheckBox) findViewById( R.id.soy);
                CheckBox otherCheckBox= (CheckBox) findViewById( R.id.other);
                int count = 0;
                if (cowsmilkCheckBox.isChecked()) count++;
                if (eggsCheckBox.isChecked()) count++;
                if (peanutsCheckBox.isChecked()) count++;
                if (fishCheckBox.isChecked()) count++;
                if (shellfishCheckBox.isChecked()) count++;
                if (treenutsCheckBox.isChecked()) count++;
                if (wheatCheckBox.isChecked()) count++;
                if (soyCheckBox.isChecked()) count++;
                if (otherCheckBox.isChecked()) count++;

                ArrayList<String> allergiesList = new ArrayList<String>();
                if (cowsmilkCheckBox.isChecked()) allergiesList.add("Cow's Milk");
                if (eggsCheckBox.isChecked()) allergiesList.add("Eggs");
                if (peanutsCheckBox.isChecked()) allergiesList.add("Peanuts");
                if (fishCheckBox.isChecked()) allergiesList.add("Fish");
                if (shellfishCheckBox.isChecked()) allergiesList.add("Shellfish");
                if (treenutsCheckBox.isChecked()) allergiesList.add("Tree Nuts");
                if (wheatCheckBox.isChecked()) allergiesList.add("Wheat");
                if (soyCheckBox.isChecked()) allergiesList.add("Soy");
                if (otherCheckBox.isChecked()) allergiesList.add(text.getText().toString());

                allergies = new String[count];
                for (int i = 0; i < count; i++) {
                    allergies[i] = allergiesList.get(i);
                }

                if (count == 0) {
//                    CharSequence text = "Nothing has been selected!";
//                    Context context = getApplicationContext();
//                    int duration = Toast.LENGTH_SHORT;
//                    Toast toast = Toast.makeText(context, text, duration);
//                    toast.show();
                    dialogShowing();
                } else {
                    MainActivity.allergyDataBase.put(MainActivity.currentUserName, allergies);
                    mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                            .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    Log.i("Point4: ", "Client Connected");
                                    PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/allergy");
                                    putDataMapReq.getDataMap().putStringArray("allergy", allergies);
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
            }
        });
    }

    private void dialogShowing() {
        dialogBuilder = new AlertDialog.Builder(AllergyActivity.this, R.style.CustomDialog);
        dialogBuilder.setTitle("Alert");
        dialogBuilder.setMessage("You haven't selected anything, do you wish to save anyway?");;
        dialogBuilder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckBox cowsmilkCheckBox = (CheckBox) findViewById(R.id.cowsmilk);
                CheckBox eggsCheckBox = (CheckBox) findViewById(R.id.eggs);
                CheckBox peanutsCheckBox = (CheckBox) findViewById(R.id.peanuts);
                CheckBox fishCheckBox = (CheckBox) findViewById(R.id.fish);
                CheckBox shellfishCheckBox = (CheckBox) findViewById(R.id.shellfish);
                CheckBox treenutsCheckBox = (CheckBox) findViewById(R.id.treenuts);
                CheckBox wheatCheckBox = (CheckBox) findViewById(R.id.wheat);
                CheckBox soyCheckBox = (CheckBox) findViewById(R.id.soy);
                CheckBox otherCheckBox = (CheckBox) findViewById(R.id.other);
                int count = 0;
                if (cowsmilkCheckBox.isChecked()) count++;
                if (eggsCheckBox.isChecked()) count++;
                if (peanutsCheckBox.isChecked()) count++;
                if (fishCheckBox.isChecked()) count++;
                if (shellfishCheckBox.isChecked()) count++;
                if (treenutsCheckBox.isChecked()) count++;
                if (wheatCheckBox.isChecked()) count++;
                if (soyCheckBox.isChecked()) count++;
                if (otherCheckBox.isChecked()) count++;

                ArrayList<String> allergiesList = new ArrayList<String>();
                if (cowsmilkCheckBox.isChecked()) allergiesList.add("Cow's Milk");
                if (eggsCheckBox.isChecked()) allergiesList.add("Eggs");
                if (peanutsCheckBox.isChecked()) allergiesList.add("Peanuts");
                if (fishCheckBox.isChecked()) allergiesList.add("Fish");
                if (shellfishCheckBox.isChecked()) allergiesList.add("Shellfish");
                if (treenutsCheckBox.isChecked()) allergiesList.add("Tree Nuts");
                if (wheatCheckBox.isChecked()) allergiesList.add("Wheat");
                if (soyCheckBox.isChecked()) allergiesList.add("Soy");
                if (otherCheckBox.isChecked()) allergiesList.add(text.getText().toString());

                allergies = new String[count];
                for (int i = 0; i < count; i++) {
                    allergies[i] = allergiesList.get(i);
                }

                MainActivity.allergyDataBase.put(MainActivity.currentUserName, allergies);
                mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i("Point4: ", "Client Connected");
                                PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/allergy");
                                putDataMapReq.getDataMap().putStringArray("allergy", allergies);
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

        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = dialogBuilder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), ProfileOption.class);
        startActivity(BackIntent);
    }
}