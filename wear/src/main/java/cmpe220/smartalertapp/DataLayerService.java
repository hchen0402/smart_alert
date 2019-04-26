package cmpe220.smartalertapp;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataLayerService extends WearableListenerService {

    /*
    private static final String TAG = "DataLayerSample";
    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String DATA_ITEM_RECEIVED_PATH = "/data-item-received";
    */

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("DataLayerService: ", "Yes");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (null == mGoogleApiClient){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Wearable.API)
                    .build();
        }
        if (!mGoogleApiClient.isConnected()){
            Log.i("Client Connected: ", "No");
            mGoogleApiClient.connect();
            Log.i("Client Connected: ", "Yes");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        Log.i("Point1: ", "Data detected");

        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.close();

        for (DataEvent event : events){
            if (event.getType() == DataEvent.TYPE_CHANGED){
                String path = event.getDataItem().getUri().getPath();
                DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                DataMap dataMap = dataMapItem.getDataMap();
                switch (path){
                    case "/personaldata":

                        Log.i("Point2: ", "Personal data");

                        String[] personalData = dataMap.getStringArray("personaldata");
                        WatchActivity.UserData.put("personalData", personalData);
                        Log.i("Visibility received: ", Boolean.toString(dataMap.containsKey("visibility")));
                        String[] visibilities = dataMap.getStringArray("visibility");
                        WatchActivity.UserData.put("visibility", visibilities);
                        break;
                    case "/allergy":
                        String[] allergies = dataMap.getStringArray("allergy");
                        WatchActivity.UserData.put("allergies", allergies);
                        break;
                    case "/preferences":
                        String[] preferences = dataMap.getStringArray("preferences");
                        WatchActivity.UserData.put("preferences", preferences);
                        Intent PulseServiceIntent = new Intent(getApplicationContext(), PulseMonitorService.class);
                        Intent ShakeServiceIntent = new Intent(getApplicationContext(), ShakeMonitorService.class);
                        if (preferences[0].equals("t")){
                            startService(PulseServiceIntent);
                        } else {
                            stopService(PulseServiceIntent);
                            Log.i("Pulse Sensor: ", "Off");
                        }
                        if (preferences[1].equals("t")){
                            startService(ShakeServiceIntent);
                        } else {
                            stopService(ShakeServiceIntent);
                            Log.i("Shake Sensor: ", "Off");
                        }
                        break;
                    case "/emergencycontact":
                        String[] contacts = dataMap.getStringArray("emergencycontact");
                        WatchActivity.UserData.put("contacts", contacts);
                        break;
//                    case "/visibility":
//                        Log.i("Visibility: ", "Received");
//                        String[] visibilities = dataMap.getStringArray("visibility");
//                        WatchActivity.UserData.put("visibility", visibilities);
//                        break;
                    default:
                        break;
                }
                    /*
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                    Toast.makeText(getApplicationContext(), "Data Changed", Toast.LENGTH_LONG).show();
                    DataMap dataMap = dataMapItem.getDataMap();

                    Log.i("Zach's Number: ", dataMap.getString("test_int"));
                    */
            }
        }

        /*
        Log.i("Point1: ", "DataChanged");
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                Log.i("Point2: ", "Type Checked");
                // DataItem changed
                DataItem item = event.getDataItem();
                Log.i("Point3: ", "Get DataItem");
                if (item.getUri().getPath().compareTo("/test") == 0) {
                    Log.i("Point4: ", "Path Compared");
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    String testInt = dataMap.getString("test_int");
                    Log.i("Test: ", testInt);
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
        */
    }
}