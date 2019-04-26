package cmpe220.smartalertapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class PulseMonitorService extends Service {

    private SensorManager mSensorManager;
    private long lastNotif;
    private long timeDiff;
    private Intent EmergencyIntent;
    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            timeDiff = System.currentTimeMillis() - lastNotif;
            float heartRate = event.values[0];
            if ((heartRate < 60 || heartRate > 100) && (timeDiff > 60000)){
                lastNotif = timeDiff + lastNotif;
                timeDiff = 0;

                EmergencyIntent = new Intent(getApplicationContext(), EmergencyActivity.class);
                EmergencyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(EmergencyIntent);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("Pulse Sensor: ", "On");

        lastNotif = 0;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE), SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
