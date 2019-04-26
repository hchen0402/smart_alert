package cmpe.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import cmpe220.smartalertapp.R;


public class MainActivity extends Activity {

    public static HashMap<String, String> userData;
    public static String currentUserName;
    public static HashMap<String, String[]> personalDataBase;
    public static HashMap<String, String[]> allergyDataBase;
    public static HashMap<String, String[]> contactInfoDataBase;
    public static HashMap<String, String[]> preferenceDataBase;
    public static HashMap<String, String[]> visibilityDataBase;
    public static boolean loggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userData = new HashMap<String, String>();
        personalDataBase = new HashMap<String, String[]> ();
        allergyDataBase =  new HashMap<String, String[]> ();
        preferenceDataBase = new HashMap<String, String[]> ();
        contactInfoDataBase = new HashMap<String, String[]> ();
        visibilityDataBase = new HashMap<String, String[]> ();
//        loggedIn = false;


        final Intent logInIntent = new Intent(this, LoginPage.class);
        startActivity(logInIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up shape, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
