package com.hfad.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mLight;

    private SensorEventListener mLightSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.d("MY_APP", event.toString());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d("MY_APP", sensor.toString() + " - " + accuracy);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        // Get sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Get the default sensor of specified type
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

       //Get the list of all sensors from the sensor manager. 
        // Store the list in a List object whose values are of type Sensor
        List<Sensor> sensorList  =
                mSensorManager.getSensorList(Sensor.TYPE_ALL);

        //Iterate over the list of sensors. For each sensor, get that sensor's official name with the getName() method,
        // and append that name to the sensorText string.
        // Each line of the sensor list is separated by the value of the line.separator property,
        // typically a newline character

        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList ) {
            sensorText.append(currentSensor.getName()).append(
                    System.getProperty("line.separator"));
        }

        //Get a reference to the TextView for the sensor list
        // and update the text of that view with the string containing the list of sensors
        TextView sensorTextView = (TextView) findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLight != null) {
            mSensorManager.registerListener(mLightSensorListener, mLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLight != null) {
            mSensorManager.unregisterListener(mLightSensorListener);
        }
    }
}

