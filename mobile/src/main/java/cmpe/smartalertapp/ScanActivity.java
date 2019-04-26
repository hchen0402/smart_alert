package cmpe.smartalertapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cmpe.smartalertapp.android.IntentIntegrator;
import cmpe.smartalertapp.android.IntentResult;
import cmpe220.smartalertapp.R;

public class ScanActivity extends Activity implements View.OnClickListener {

    public static String scanContent;
    public static String scanFormat;

    private Button scanBtn, translateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_activity);
        scanBtn = (Button)findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);
        translateBtn = (Button)findViewById(R.id.translate_menu);
        translateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId()==R.id.translate_menu) {
                    Intent Ocr_activity = new Intent(getApplicationContext(), OcrActivity.class);
                    startActivity(Ocr_activity);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){

            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //open_camera_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            scanContent = scanningResult.getContents();
            scanFormat = scanningResult.getFormatName();
            //Log.d("Content:", scanContent);
            //Log.d("Format:", scanFormat);
            Intent scan_activity = new Intent(this, ScanResultActivity.class);
            startActivity(scan_activity);
        } else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        startActivity(BackIntent);
    }
}

