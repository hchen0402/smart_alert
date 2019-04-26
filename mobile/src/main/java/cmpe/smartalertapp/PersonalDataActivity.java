package cmpe.smartalertapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import cmpe220.smartalertapp.R;

public class PersonalDataActivity extends Activity {

    private AlertDialog.Builder dialogBuilder;
    private GoogleApiClient mGoogleApiClient;
    private Intent profileOption;
    private EditText name;
    private EditText month;
    private EditText day;
    private EditText year;
    private EditText ft;
    private EditText in;
    private EditText weight;
    private EditText medicalId;
    private EditText healthCare;
    private EditText driverLisence;
    private Spinner weightSpinner;
    private Spinner bloodTypeSpinner;
    private String[] textFields;
    static private String[] textFieldsCopy;
    static private boolean returning;
    static public String[] visibilities;
    static public String[] visibilitiesCopy;
    private String viewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_data);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            viewMode = extras.getString("view");
        }
        else
        {
            viewMode = "Edit";
        }

        Button saveButton = (Button)findViewById(R.id.saveButton);
        Button visibilityButton = (Button)findViewById(R.id.visibilityButton);
        final Intent visibilityIntent = new Intent(this, VisibilityActivity.class);

        weightSpinner = (Spinner) findViewById(R.id.weightSpinner);
        ArrayAdapter<CharSequence> weight_adapter = ArrayAdapter.createFromResource(this,
                R.array.weight_items, R.layout.spinner_row);
        weight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        weightSpinner.setAdapter(weight_adapter);

        bloodTypeSpinner = (Spinner) findViewById(R.id.bloodTypeSpinner);
        ArrayAdapter<CharSequence> bt_adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_type, R.layout.spinner_row);
        bt_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        bloodTypeSpinner.setAdapter(bt_adapter);
        profileOption = new Intent(this, ProfileOption.class);
        name = (EditText)findViewById(R.id.nameInput);
        month = (EditText)findViewById(R.id.monthInput);
        day = (EditText)findViewById(R.id.dayInput);
        year = (EditText)findViewById(R.id.yearInput);
        ft = (EditText)findViewById(R.id.ft_input);
        in = (EditText)findViewById(R.id.in_input);
        weight = (EditText)findViewById(R.id.weightInput);
        medicalId = (EditText)findViewById(R.id.medicalIDInput);
        healthCare = (EditText)findViewById(R.id.healthcareInput);
        driverLisence = (EditText)findViewById(R.id.driversLicenseInput);

        String nameContent = MainActivity.currentUserName;
        textFields = MainActivity.personalDataBase.get(nameContent);
        visibilities = MainActivity.visibilityDataBase.get(nameContent);

//        if (MainActivity.personalDataBase.get(nameContent) != null) {
//            String[] personalInfo = MainActivity.personalDataBase.get(nameContent);
//            Log.i("CurrentUserName1: ", nameContent);
//            name.setText(personalInfo[0]);
//            //name.setEnabled(false);
//
//            String[] birthday = personalInfo[1].split("/");
//
//            if (birthday.length == 3) {
//                month.setText(birthday[0]);
//                day.setText(birthday[1]);
//                year.setText(birthday[2]);
//            }
//
//
//            String[] heightInfo = personalInfo[2].split(" ");
//            if (heightInfo.length == 4) {
//                ft.setText(heightInfo[0]);
//                in.setText(heightInfo[2]);
//            }
//
//            if (personalInfo[3].split(" ").length == 2)
//                weight.setText(personalInfo[3].split(" ")[0]);
//            ArrayAdapter myUnitAdap = (ArrayAdapter)weightSpinner.getAdapter();
//            Log.i("weight: ", personalInfo[3].split(" ")[1]);
//            weightSpinner.setSelection(myUnitAdap.getPosition(personalInfo[3].split(" ")[1]));
//
//            ArrayAdapter myBloodTypeAdap = (ArrayAdapter)bloodTypeSpinner.getAdapter();
//            Log.i("weight: ", "l: "+personalInfo[4].length());
//            Log.i("blood: ", "length: "+myBloodTypeAdap.getPosition(personalInfo[4]));
//            bloodTypeSpinner.setSelection(myBloodTypeAdap.getPosition(personalInfo[4]));
//
//            medicalId.setText(personalInfo[5]);
//            healthCare.setText(personalInfo[6]);
//            driverLisence.setText(personalInfo[7]);
//        }
        if (returning){
            name.setText(textFieldsCopy[0]);

            String[] birthday = textFieldsCopy[1].split("/");

            if (birthday.length == 3) {
                month.setText(birthday[0]);
                day.setText(birthday[1]);
                year.setText(birthday[2]);
            }


            String[] heightInfo = textFieldsCopy[2].split(" ");
            if (heightInfo.length == 4) {
                ft.setText(heightInfo[0]);
                in.setText(heightInfo[2]);
            }

            if (textFieldsCopy[3].split(" ").length == 2)
                weight.setText(textFieldsCopy[3].split(" ")[0]);
            ArrayAdapter myUnitAdap = (ArrayAdapter)weightSpinner.getAdapter();
            weightSpinner.setSelection(myUnitAdap.getPosition(textFieldsCopy[3].split(" ")[1]));

            ArrayAdapter myBloodTypeAdap = (ArrayAdapter)bloodTypeSpinner.getAdapter();
            bloodTypeSpinner.setSelection(myBloodTypeAdap.getPosition(textFieldsCopy[4]));

            medicalId.setText(textFieldsCopy[5]);
            healthCare.setText(textFieldsCopy[6]);
            driverLisence.setText(textFieldsCopy[7]);

            visibilities = visibilitiesCopy;
            textFields = textFieldsCopy;
            returning = false;
        } else if (textFields != null) {
            name.setText(textFields[0]);

            String[] birthday = textFields[1].split("/");

            if (birthday.length == 3) {
                month.setText(birthday[0]);
                day.setText(birthday[1]);
                year.setText(birthday[2]);
            }


            String[] heightInfo = textFields[2].split(" ");
            if (heightInfo.length == 4) {
                ft.setText(heightInfo[0]);
                in.setText(heightInfo[2]);
            }

            if (textFields[3].split(" ").length == 2)
                weight.setText(textFields[3].split(" ")[0]);
            ArrayAdapter myUnitAdap = (ArrayAdapter)weightSpinner.getAdapter();
            Log.i("weight: ", textFields[3].split(" ")[1]);
            weightSpinner.setSelection(myUnitAdap.getPosition(textFields[3].split(" ")[1]));

            ArrayAdapter myBloodTypeAdap = (ArrayAdapter)bloodTypeSpinner.getAdapter();
            Log.i("weight: ", "l: "+textFields[4].length());
            Log.i("blood: ", "length: "+myBloodTypeAdap.getPosition(textFields[4]));
            bloodTypeSpinner.setSelection(myBloodTypeAdap.getPosition(textFields[4]));

            medicalId.setText(textFields[5]);
            healthCare.setText(textFields[6]);
            driverLisence.setText(textFields[7]);

            visibilitiesCopy = visibilities;
            textFieldsCopy = textFields;
        } else if (MainActivity.userData.containsKey("name")) {
            name.setText(MainActivity.userData.get("name"));
            textFields = new String[8];
            textFields[0] = name.getText().toString();

            visibilitiesCopy = visibilities;
            textFieldsCopy = textFields;
        } else {
            textFields = new String[8];

            visibilitiesCopy = visibilities;
            textFieldsCopy = textFields;
        }

        visibilityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String weightUnit = weightSpinner.getSelectedItem().toString();
                final String bloodType = bloodTypeSpinner.getSelectedItem().toString();
                textFieldsCopy[0] = name.getText().toString();
                textFieldsCopy[1] = month.getText().toString() + "/" + day.getText().toString() + "/" + year.getText().toString();
                textFieldsCopy[2] = ft.getText().toString() + " ft " + in.getText().toString() + " in";
                textFieldsCopy[3] = weight.getText().toString() + " " + weightUnit;
                textFieldsCopy[4] = bloodType;
                textFieldsCopy[5] = medicalId.getText().toString();
                textFieldsCopy[6] = healthCare.getText().toString();
                textFieldsCopy[7] = driverLisence.getText().toString();
                returning = true;
                startActivity(visibilityIntent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean missing = false;
                final String weightUnit = weightSpinner.getSelectedItem().toString();
                final String bloodType = bloodTypeSpinner.getSelectedItem().toString();

                if (name.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (month.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (day.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (year.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (ft.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (in.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (weight.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (medicalId.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (healthCare.getText().toString().trim().length() == 0) {
                    missing = true;
                } else if (driverLisence.getText().toString().trim().length() == 0) {
                    missing = true;
                }
                if (missing) {
                    dialogShowing();
                } else {
                    final String[] personalData = new String[8];
                    personalData[0] = name.getText().toString();
                    personalData[1] = month.getText().toString() + "/" + day.getText().toString() + "/" + year.getText().toString();
                    Log.d("birthday: ", personalData[1]);
                    personalData[2] = ft.getText().toString() + " ft " + in.getText().toString() + " in";
                    Log.d("height: ", personalData[2]);
                    personalData[3] = weight.getText().toString() + " " + weightUnit;
                    Log.d("weight: ", personalData[3]);
                    personalData[4] = bloodType;
                    personalData[5] = medicalId.getText().toString();
                    personalData[6] = healthCare.getText().toString();
                    personalData[7] = driverLisence.getText().toString();

                    MainActivity.personalDataBase.put(MainActivity.currentUserName, personalData);
                    if (visibilities == null){
                        visibilities = new String[] {"f", "f", "f", "f", "f", "f", "f", "f"};

                    }
                    MainActivity.visibilityDataBase.put(MainActivity.currentUserName, visibilities);

                    mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                            .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/personaldata");
                                    putDataMapReq.getDataMap().putStringArray("personaldata", personalData);
                                    putDataMapReq.getDataMap().putStringArray("visibility", visibilities);
                                    PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
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
                    startActivity(profileOption);
                }
            }
        });

        if (viewMode.equals("Read only")){
            name.setEnabled(false);
            month.setEnabled(false);
            day.setEnabled(false);
            year.setEnabled(false);
            ft.setEnabled(false);
            in.setEnabled(false);
            weight.setEnabled(false);
            medicalId.setEnabled(false);
            healthCare.setEnabled(false);
            driverLisence.setEnabled(false);
            bloodTypeSpinner.setEnabled(false);
            weightSpinner.setEnabled(false);
            visibilityButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        }
    }

    private void dialogShowing() {
        dialogBuilder = new AlertDialog.Builder(PersonalDataActivity.this, R.style.CustomDialog);
        dialogBuilder.setTitle("Alert");
        dialogBuilder.setMessage("Some fields are missing, do you wish to save anyway?");
        dialogBuilder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String weightUnit = weightSpinner.getSelectedItem().toString();
                String bloodType = bloodTypeSpinner.getSelectedItem().toString();
                final String[] personalData = new String[8];
                personalData[0] = name.getText().toString();
                personalData[1] = month.getText().toString() + "/" + day.getText().toString() + "/" + year.getText().toString();
                personalData[2] = ft.getText().toString() + " ft " + in.getText().toString() + " in";
                personalData[3] = weight.getText().toString() + " " + weightUnit;
                personalData[4] = bloodType;
                personalData[5] = medicalId.getText().toString();
                personalData[6] = healthCare.getText().toString();
                personalData[7] = driverLisence.getText().toString();

                MainActivity.personalDataBase.put(MainActivity.currentUserName, personalData);
                if (visibilities == null){
                    visibilities = new String[] {"f", "f", "f", "f", "f", "f", "f", "f"};
                }
                MainActivity.visibilityDataBase.put(MainActivity.currentUserName, visibilities);

                mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/personaldata");
                                putDataMapReq.getDataMap().putStringArray("personaldata", personalData);
                                putDataMapReq.getDataMap().putStringArray("visibility", visibilities);
                                PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
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
                startActivity(profileOption);
            }
        });

        dialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = dialogBuilder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        if (viewMode.equals("Edit")) {
            if (MainActivity.visibilityDataBase.containsKey(MainActivity.currentUserName)) {
                visibilities = MainActivity.visibilityDataBase.get(MainActivity.currentUserName);
            } else {
                visibilities = null;
            }
            visibilitiesCopy = visibilities;
            if (MainActivity.personalDataBase.containsKey(MainActivity.currentUserName)) {
                textFields = MainActivity.personalDataBase.get(MainActivity.currentUserName);
            } else {
                textFields = null;
            }
            Intent BackIntent = new Intent(getApplicationContext(), ProfileOption.class);
            startActivity(BackIntent);
        } else if (viewMode.equals("Read only")){
            Intent BackIntent = new Intent(getApplicationContext(), EmergencyResponseActivity.class);
            startActivity(BackIntent);
        }
    }
}

