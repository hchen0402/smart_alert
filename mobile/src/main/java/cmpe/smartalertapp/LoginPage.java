package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import cmpe220.smartalertapp.R;

public class LoginPage extends Activity {

    private Intent EmergencyListenerIntent;
    private EditText username;
    private EditText password;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv= (ImageView)findViewById(R.id.imageView1);
        iv.setImageResource(R.drawable.er_icon);
        MainActivity.loggedIn = false;

        EmergencyListenerIntent = new Intent(getApplicationContext(), EmergencyListenerService.class);
        startService(EmergencyListenerIntent);

        final Button createProfileButton = (Button) findViewById(R.id.createProfileButton);
        final Intent NameIntent = new Intent(this, NameActivity.class);

        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final Intent HomeScreenIntent = new Intent(this, HomeScreenActivity.class);
        username = (EditText) findViewById(R.id.email_address);
        password = (EditText) findViewById(R.id.password);

        createProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Go to Home Screen");
                startActivity(NameIntent);
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Login");
                String inputEmail = username.getText().toString();
                String inputPassword = password.getText().toString();
                if (MainActivity.userData.containsKey(inputEmail)) {
                    if (MainActivity.userData.get(inputEmail).equals(inputPassword)) {
                        MainActivity.currentUserName = inputEmail;

                        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                    @Override
                                    public void onConnected(Bundle bundle) {
                                        if (MainActivity.personalDataBase.containsKey(MainActivity.currentUserName)) {
                                            PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/personaldata");
                                            String[] personalData = MainActivity.personalDataBase.get(MainActivity.currentUserName);
                                            putDataMapReq.getDataMap().putStringArray("personaldata", personalData);
                                            if (MainActivity.visibilityDataBase.containsKey(MainActivity.currentUserName)) {
                                                String[] visibilities = MainActivity.visibilityDataBase.get(MainActivity.currentUserName);
                                                putDataMapReq.getDataMap().putStringArray("visibility", visibilities);
                                            }

                                            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                                            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
                                        }
                                        if (MainActivity.allergyDataBase.containsKey(MainActivity.currentUserName)) {
                                            PutDataMapRequest putDataMapReq1 = PutDataMapRequest.create("/allergy");
                                            String[] allergies = MainActivity.allergyDataBase.get(MainActivity.currentUserName);
                                            putDataMapReq1.getDataMap().putStringArray("allergy", allergies);
                                            PutDataRequest putDataReq1 = putDataMapReq1.asPutDataRequest();
                                            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq1);
                                        }
                                        if (MainActivity.contactInfoDataBase.containsKey(MainActivity.currentUserName)) {
                                            PutDataMapRequest putDataMapReq2 = PutDataMapRequest.create("/emergencycontact");
                                            String[] contactInfo = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
                                            putDataMapReq2.getDataMap().putStringArray("emergencycontact", contactInfo);
                                            PutDataRequest putDataReq2 = putDataMapReq2.asPutDataRequest();
                                            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq2);
                                        }
                                        if (MainActivity.preferenceDataBase.containsKey(MainActivity.currentUserName)) {
                                            String[] preferenceStrings = MainActivity.preferenceDataBase.get(MainActivity.currentUserName);
                                            PutDataMapRequest putDataMapReq3 = PutDataMapRequest.create("/preferences");
                                            putDataMapReq3.getDataMap().putStringArray("preferences", preferenceStrings);
                                            PutDataRequest putDataReq3 = putDataMapReq3.asPutDataRequest();
                                            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq3);
                                        }



                                        //mGoogleApiClient.disconnect();
                                    }

                                    @Override
                                    public void onConnectionSuspended(int i) {
                                    }
                                })
                                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                                    @Override
                                    public void onConnectionFailed(ConnectionResult connectionResult) {

                                    }
                                })
                                .addApi(Wearable.API)
                                .build();
                        mGoogleApiClient.connect();

                        startActivity(HomeScreenIntent);
                    } else {
                        CharSequence text = "Password incorrect. Please try again.";
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                } else {
                    CharSequence text = "The email you entered does not belong to any account.";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });
    }
}
