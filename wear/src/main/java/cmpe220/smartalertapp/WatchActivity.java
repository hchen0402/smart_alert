package cmpe220.smartalertapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class WatchActivity extends Activity {

    public static HashMap UserData;
    private Intent DataLayerIntent;
    private Intent HomeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserData = new HashMap();

        DataLayerIntent = new Intent(getApplicationContext(), DataLayerService.class);
        startService(DataLayerIntent);

        HomeIntent = new Intent(getApplicationContext(), WatchActivityNew.class);
        startActivity(HomeIntent);
    }
}
