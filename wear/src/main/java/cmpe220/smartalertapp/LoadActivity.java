package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;


public class LoadActivity extends Activity {

    private RelativeLayout LoadScreen;
    private Intent LoadIntent;
    private Node handheld;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
//        LoadScreen = (RelativeLayout) findViewById(R.id.emergency_load);
//        LoadScreen.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                LoadIntent = new Intent(getApplicationContext(), WatchActivity.class);
//                startActivity(LoadIntent);
//                //finish();     I really don't know..
//                //getParent().finish();
//                return true;
//            }
//        });

        // yada yada something with progress bar

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        setupApiClient();

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

        Intent msgIntent = new Intent(getApplicationContext(), MessageActivity.class);
        startActivity(msgIntent);

        //finish();


    }

    public void setupApiClient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                CapabilityApi.GetCapabilityResult commResult =
                        Wearable.CapabilityApi.getCapability(mGoogleApiClient,
                                "emergency", CapabilityApi.FILTER_REACHABLE).await();
                if (commResult.getCapability().getNodes().size() > 0) {
                    handheld = commResult.getCapability().getNodes().iterator().next();
                }

                if (handheld != null) {
                    Wearable.MessageApi.sendMessage(mGoogleApiClient, handheld.getId(),
                            "emergency", null).await();
                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load, menu);
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
