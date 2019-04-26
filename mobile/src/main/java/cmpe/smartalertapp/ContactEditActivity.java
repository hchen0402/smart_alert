package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import cmpe220.smartalertapp.R;


public class ContactEditActivity extends Activity {

    private EditText name;
    private EditText relationship;
    private EditText phone;
    private Button Cancel;
    private Button Save;
    private Intent ButtonIntent;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        name = (EditText) findViewById(R.id.name_input);
        relationship = (EditText) findViewById(R.id.relationship_input);
        phone = (EditText) findViewById(R.id.phone_input);
        Cancel = (Button) findViewById(R.id.cancel_contect);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonIntent = new Intent(getApplicationContext(), ContactListActivity.class);
                startActivity(ButtonIntent);
            }
        });

        Save = (Button) findViewById(R.id.save_contact);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Point1: ", "On Click");
                mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/test");
                                putDataMapReq.getDataMap().putStringArray("test_int", new String[]{phone.getText().toString(), "Zach"});
                                PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);

                                //mGoogleApiClient.disconnect();
                                finish();
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

                Toast.makeText(getApplicationContext(), "Testing Data Layer", Toast.LENGTH_LONG).show();

                //finish();

                /*
                setContentView(R.layout.activity_contact_list);
                LinearLayout ContactList = (LinearLayout) findViewById(R.id.contact_list);
                Log.i("Layout Found: ", Boolean.toString(ContactList != null));

                String nameInput = name.getText().toString();
                TextView nameText = new TextView(ContactList.getContext());
                nameText.setText("Name: " + nameInput);

                String relationshipInput = relationship.getText().toString();
                TextView relationshipText = new TextView(ContactList.getContext());
                relationshipText.setText("Relationship: " + relationshipInput);

                String phoneInput = phone.getText().toString();
                TextView phoneText = new TextView(ContactList.getContext());
                phoneText.setText("Phone: " + phoneInput);

                LinearLayout Contact = new LinearLayout(ContactList.getContext());
                Contact.setOrientation(LinearLayout.VERTICAL);
                Contact.addView(nameText);
                Contact.addView(relationshipText);
                Contact.addView(phoneText);

                ContactList.addView(Contact);
                ButtonIntent = new Intent(getApplicationContext(), ContactListActivity.class);
                startActivity(ButtonIntent);
                */
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_edit, menu);
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
