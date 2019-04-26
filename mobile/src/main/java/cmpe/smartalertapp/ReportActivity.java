package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

import android.telephony.SmsManager;
import android.util.Log;

import cmpe220.smartalertapp.R;

import static android.content.ContentValues.TAG;


public class ReportActivity extends Activity {

    private EditText injuriesNumber;
    private EditText customLocation;
    private Intent smsIntent;
    private Geocoder geocoder;
    private AppLocationService appLocationService;
    String currentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_data);
        geocoder = new Geocoder(this, Locale.ENGLISH);

        CheckBox accidentBox= (CheckBox) findViewById( R.id.accident);
        CheckBox utilityBox= (CheckBox) findViewById( R.id.utility);
        CheckBox fireBox= (CheckBox) findViewById( R.id.fire);
        CheckBox disorderBox= (CheckBox) findViewById( R.id.disorder);
        CheckBox robberyBox= (CheckBox) findViewById( R.id.robbery);
        CheckBox powerFailureBox= (CheckBox) findViewById( R.id.power_failure);
        CheckBox theftBox= (CheckBox) findViewById( R.id.theft);
        CheckBox currentLocationBox= (CheckBox) findViewById( R.id.current_location);
        CheckBox customLocationBox= (CheckBox) findViewById( R.id.custom_location);

        injuriesNumber = (EditText)findViewById( R.id.injuries_number);
        customLocation = (EditText)findViewById( R.id.custom_location_text);
        customLocation.setEnabled(false);
        Button sendButton = (Button) findViewById(R.id.continueButton);
        smsIntent = new Intent(Intent.ACTION_SENDTO);
        String smsNumber = String.format("smsto: %s", "511");
        smsIntent.setData(Uri.parse(smsNumber));

        customLocationBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    customLocation.setEnabled(true);
                } else {
                    customLocation.setEnabled(false);
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                CheckBox accidentBox= (CheckBox) findViewById( R.id.accident);
                CheckBox utilityBox= (CheckBox) findViewById( R.id.utility);
                CheckBox fireBox= (CheckBox) findViewById( R.id.fire);
                CheckBox disorderBox = (CheckBox) findViewById( R.id.disorder);
                CheckBox robberyBox = (CheckBox) findViewById( R.id.robbery);
                CheckBox powerFailureBox = (CheckBox) findViewById( R.id.power_failure);
                CheckBox theftBox = (CheckBox) findViewById( R.id.theft);
                CheckBox currentLocationBox = (CheckBox) findViewById( R.id.current_location);
                CheckBox customLocationBox = (CheckBox) findViewById( R.id.custom_location);

                ArrayList<String> ReportList = new ArrayList<String>();
                if (accidentBox.isChecked()) ReportList.add("Traffic accident");
                if (utilityBox.isChecked()) ReportList.add("Utility accident");
                if (fireBox.isChecked()) ReportList.add("Fire accident");
                if (disorderBox.isChecked()) ReportList.add("Disorder");
                if (robberyBox.isChecked()) ReportList.add("Robbery");
                if (powerFailureBox.isChecked()) ReportList.add("Power Failure");
                if (theftBox.isChecked()) ReportList.add("Theft");
                ReportList.add("happens at");
                if (currentLocationBox.isChecked()) {
                    Log.d("d", "Enter Current location");
//                    appLocationService = new AppLocationService(
//                            ReportActivity.this);
//                    Location location = appLocationService
//                            .getLocation(LocationManager.GPS_PROVIDER);
//                    if (location != null) {
//                        double latitude = location.getLatitude();
//                        double longitude = location.getLongitude();
//                        LocationAddress locationAddress = new LocationAddress();
//                        locationAddress.getAddressFromLocation(latitude, longitude,
//                                getApplicationContext(), new GeocoderHandler());
//                        ReportList.add(currentAddress);
//                    } else {
//
//                    }
                    ReportList.add("Current Location");

                }
                if (customLocationBox.isChecked())
                    ReportList.add(customLocation.getText().toString());

                ReportList.add(".");
                ReportList.add(injuriesNumber.getText().toString());
                ReportList.add("people are injured.");

                String msg = "";
                for (String s : ReportList){
                    msg += s + "\t";
                }

                smsIntent.putExtra("sms_body", msg);
                //sendSMS("5556", "test");

                if (smsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(smsIntent);
                } else {
                    Log.d(TAG, "Can't resolve app for ACTION_SENDTO Intent");
                }

//                AlertDialog.Builder builder = new AlertDialog.Builder(ReportActivity.this);
//
//                builder.setCancelable(true);
//
//                builder.setTitle("Message below is sent to 911");
//                builder.setMessage(msg);
//
//                // Setting Negative "Cancel" Button
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        dialog.cancel();
//                    }
//                });
//
//                // Setting Positive "Yes" Button
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//
//                builder.show();
            }
        });

    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            currentAddress = locationAddress;
        }
    }
}
