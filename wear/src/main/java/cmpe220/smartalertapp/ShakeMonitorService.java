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

public class ShakeMonitorService extends Service {

    static public boolean running;

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private final float accelThresh = 50f;     //change as needed
    private long lastNotif;
    private long timeDiff;
    private Intent EmergencyIntent;
    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            timeDiff = System.currentTimeMillis() - lastNotif;

            if (mAccel > accelThresh && timeDiff > 5000) {
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

        Log.i("Shake Sensor: ", "On");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        lastNotif = 0;

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
