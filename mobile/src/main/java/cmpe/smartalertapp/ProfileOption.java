package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cmpe220.smartalertapp.R;

public class ProfileOption extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_options);

        final Button personalDataButton = (Button) findViewById(R.id.personalDataButton);
        final Intent personalDataIntent = new Intent(this, PersonalDataActivity.class);

        final Button allergyButton = (Button) findViewById(R.id.allergyButton);
        final Intent allergyButtonIntent = new Intent(this, AllergyActivity.class);

        final Button emergencyButton = (Button) findViewById(R.id.emergencyContactsButton);
        final Intent emergencyContactsIntent = new Intent(this, EmergencyContactsActivity.class);

        final Button preferenceButton = (Button) findViewById(R.id.preferenceButton);
        final Intent preferenceIntent = new Intent(this, PreferenceActivity.class);

        personalDataButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Enter Personal Data");
                startActivity(personalDataIntent);
            }
        });

        allergyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Enter Allergies");
                startActivity(allergyButtonIntent);
            }
        });

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Enter Emergency Contacts");
                startActivity(emergencyContactsIntent);
            }
        });

        preferenceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("d", "Enter Preference");
                startActivity(preferenceIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(BackIntent);
    }
}
