package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cmpe220.smartalertapp.R;

public class EmergencyContactsActivity extends Activity {
    private LinearLayout Contact1;
    private LinearLayout Contact2;
    private LinearLayout Contact3;
    //static public int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contacts);

        Contact1 = (LinearLayout) findViewById(R.id.edit_contact_1);
        Contact2 = (LinearLayout) findViewById(R.id.edit_contact_2);
        Contact3 = (LinearLayout) findViewById(R.id.edit_contact_3);
        final Button addContactButton = (Button) findViewById(R.id.addContact);
        final Intent contactInfoIntent = new Intent(this, ContactInfo.class);

        if (MainActivity.contactInfoDataBase.containsKey(MainActivity.currentUserName)) {
            Log.d("info: ", MainActivity.contactInfoDataBase.get(MainActivity.currentUserName)[0]);
            String[] contactInfo = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
//            TextView contactTitle = (TextView)findViewById(R.id.contactTitle);

            TextView i_name1 = (TextView)findViewById(R.id.i_name1);
            TextView i_name2 = (TextView)findViewById(R.id.i_name2);
            TextView i_name3 = (TextView)findViewById(R.id.i_name3);

            TextView name1 = (TextView)findViewById(R.id.name1);
            TextView name2 = (TextView)findViewById(R.id.name2);
            TextView name3 = (TextView)findViewById(R.id.name3);

            TextView i_relationship1 = (TextView)findViewById(R.id.i_relationship1);
            TextView i_relationship2 = (TextView)findViewById(R.id.i_relationship2);
            TextView i_relationship3 = (TextView)findViewById(R.id.i_relationship3);

            TextView relationship1 = (TextView)findViewById(R.id.relationship1);
            TextView relationship2 = (TextView)findViewById(R.id.relationship2);
            TextView relationship3 = (TextView)findViewById(R.id.relationship3);

            TextView i_phone1 = (TextView)findViewById(R.id.i_phone1);
            TextView i_phone2 = (TextView)findViewById(R.id.i_phone2);
            TextView i_phone3 = (TextView)findViewById(R.id.i_phone3);

            TextView phone1 = (TextView)findViewById(R.id.phone1);
            TextView phone2 = (TextView)findViewById(R.id.phone2);
            TextView phone3 = (TextView)findViewById(R.id.phone3);

            ImageView person1 = (ImageView)findViewById(R.id.person1);
            ImageView person2 = (ImageView)findViewById(R.id.person2);
            ImageView person3 = (ImageView)findViewById(R.id.person3);

            if (contactInfo.length >= 3) {
//                contactTitle.setText("Contact Information: ");
                i_name1.setText("Name");
                name1.setText(contactInfo[0]);

                i_relationship1.setText("Relationship");
                relationship1.setText(contactInfo[1]);

                i_phone1.setText("Phone");
                phone1.setText(contactInfo[2]);

                person1.setBackgroundColor(getResources().getColor(R.color.pink));
                Contact1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent EditIntent = new Intent(getApplicationContext(), ContactInfo.class);
                        EditIntent.putExtra("view", "Edit");
                        EditIntent.putExtra("order", 0);
                        startActivity(EditIntent);
                    }
                });
            }
            if (contactInfo.length >= 6) {
//                contactTitle.setText("Contact Information: ");
                i_name2.setText("Name");
                name2.setText(contactInfo[3]);

                i_relationship2.setText("Relationship");
                relationship2.setText(contactInfo[4]);

                i_phone2.setText("Phone");
                phone2.setText(contactInfo[5]);

                person2.setBackgroundColor(getResources().getColor(R.color.pink));
                Contact2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent EditIntent = new Intent(getApplicationContext(), ContactInfo.class);
                        EditIntent.putExtra("view", "Edit");
                        EditIntent.putExtra("order", 3);
                        startActivity(EditIntent);
                    }
                });
            }
            if (contactInfo.length == 9) {
//                contactTitle.setText("Contact Information: ");
                i_name3.setText("Name");
                name3.setText(contactInfo[6]);

                i_relationship3.setText("Relationship");
                relationship3.setText(contactInfo[7]);

                i_phone3.setText("Phone");
                phone3.setText(contactInfo[8]);

                person3.setBackgroundColor(getResources().getColor(R.color.pink));
                Contact3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent EditIntent = new Intent(getApplicationContext(), ContactInfo.class);
                        EditIntent.putExtra("view", "Edit");
                        EditIntent.putExtra("order", 6);
                        startActivity(EditIntent);
                    }
                });
            }

        }

        addContactButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] userContacts = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
                if ((userContacts ==  null) || (userContacts.length < 9)){
                    startActivity(contactInfoIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "This app only supports\n3 emergency contacts", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), ProfileOption.class);
        startActivity(BackIntent);
    }
}
