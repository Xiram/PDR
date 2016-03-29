package com.example.guillaume2.pdr;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StepMonitor extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor stepDetector;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private TextView TextView_count;
    private TextView TextView_azimuth;
    private MapView m_Mapview;
    private Button button;
    float azimuth;
    private int steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_monitor);


        TextView_count = (TextView) findViewById(R.id.counter);
        TextView_azimuth = (TextView) findViewById(R.id.azimuth);
        m_Mapview = (MapView) findViewById(R.id.mapView);
        button=(Button) findViewById(R.id.button);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR); // problème
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        steps=0;

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_Mapview.move(azimuth,10f);
            }
        });


        // check if device supports step_detector

        Context context = this;
        PackageManager packageManager = context.getPackageManager();

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)) {
            //yes
            Log.i("sensor", "This device has step_detector!");
        }else{
            //no
            Log.i("sensor", "This device has no step_detector!");
        }

    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }



    float[] mGravity;
    float[] mGeomagnetic;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR)
        {
           // if (event.values[0] == 1.0f) {
                steps++;

                //TextView_count.setText(Integer.toString(steps));
                TextView_count.setText("it works !");
                TextView_azimuth.setText(Float.toString(azimuth));

                m_Mapview.move(azimuth, 10f);
           // }

        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = orientation[0];


            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
