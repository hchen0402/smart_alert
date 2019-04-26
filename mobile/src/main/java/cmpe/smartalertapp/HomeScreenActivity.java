package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import cmpe220.smartalertapp.R;

public class HomeScreenActivity extends Activity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

//        spinner = (Spinner) findViewById(R.id.languages);
////        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.languages_array, R.layout.spinner_row);
////        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
////        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

        /*
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.UserData.put("profile_language", spinner.getSelectedItem().toString());
            }
        });
        */

        final Button editHealthProfileButton = (Button) findViewById(R.id.editHealthProfileButton);
        final Intent profileOption = new Intent(this, ProfileOption.class);
        final Button sosButton = (Button) findViewById(R.id.sosButton);
        final Intent emergencyResponseIntent = new Intent(this, EmergencyResponseActivity.class);
        final Button reportButton = (Button) findViewById(R.id.report);
        final Intent reportIntent = new Intent(this, ReportActivity.class);
        final Button logoutButton = (Button) findViewById(R.id.logout);
        final Intent logoutIntent = new Intent(this, LoginPage.class);

        MainActivity.loggedIn = true;

        editHealthProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Enter Profile options");
                startActivity(profileOption);
            }
        });

        sosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Enter Emergency Response");
                startActivity(emergencyResponseIntent);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Enter Incident report");
                startActivity(reportIntent);
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Enter Scan");
                EmailActivity.userName = null;
                EmergencyResponseActivity.alarmOn = false;
                EmergencyResponseActivity.messageContacts = false;
                EmergencyResponseActivity.callForHelp = false;
                PersonalDataActivity.visibilities = null;
                PersonalDataActivity.visibilitiesCopy = null;
                MainActivity.currentUserName = null;
                startActivity(logoutIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
