package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import cmpe220.smartalertapp.R;


public class CallContactsActivity extends Activity {

    String phoneNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_contacts);
        String [] ContactsCopy = MainActivity.contactInfoDataBase.get(MainActivity.currentUserName);
        if (ContactsCopy != null && ContactsCopy.length > 0){
            LinearLayout ContactsLayout = (LinearLayout) findViewById(R.id.call_contacts_layout);
            LinearLayout.LayoutParams p = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < ContactsCopy.length; i += 3) {
                Button ContactButton = new Button(this);
                ContactButton.setGravity(0x11);
                ContactButton.setLayoutParams(p);
                ContactButton.setTextColor(Color.WHITE);
                ContactButton.setTextSize(20);

                String phoneOld = ContactsCopy[i+2];
                phoneNew = "";
                for (int j = 0; j < phoneOld.length(); j++){
                    if (Character.isDigit(phoneOld.charAt(j))){
                        phoneNew = phoneNew+phoneOld.charAt(j);
                    }
                }
                String phoneDisplay;
                if (phoneNew.length() == 10){
                    phoneDisplay = "("+phoneNew.substring(0,3)+
                            ") "+phoneNew.substring(3,6)+"-"+phoneNew.substring(6,phoneNew.length());
                } else {
                    phoneDisplay = phoneOld;
                }
                ContactButton.setText(ContactsCopy[i]+"\n"+
                        ContactsCopy[i+1]+"\n"+
                        phoneDisplay);
                ContactButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (phoneNew.length() == 10) {
                            Intent CallIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNew)); // change to local emergency number
                            startActivity(CallIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to dial phone number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if (i == 0){
                    ContactButton.setPadding(40, 40, 40, 40);
                } else {
                    ContactButton.setPadding(40, 40, 40, 40);
                }
                ContactsLayout.addView(ContactButton);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_call_contacts, menu);
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

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), EmergencyResponseActivity.class);
        startActivity(BackIntent);
    }
}
