package com.example.wearable;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    Sensor gyrometer, accelerometer;
    TextView xValue, yValue, zValue, xGyroValue, yGyroValue, zGyroValue;
    MediaPlayer notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xValue = (TextView)findViewById(R.id.xValue);
        yValue = (TextView)findViewById(R.id.yValue);
        zValue = (TextView)findViewById(R.id.zValue);
        xGyroValue = (TextView)findViewById(R.id.xGyroValue);
        yGyroValue = (TextView)findViewById(R.id.yGyroValue);
        zGyroValue = (TextView)findViewById(R.id.zGyroValue);
        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gyrometer = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        notification = MediaPlayer.create(this, R.raw.scream);
        if(gyrometer != null) {
            sensorManager.registerListener(MainActivity.this, gyrometer, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered gyrometer Listerner");
        }else{
            xValue.setText("no gyrometer detected");
        }
        if(accelerometer != null) {
            sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered accelerometer Listerner");
        }else{
            xValue.setText("no accelerometer detected");
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.d(TAG, "onSensorChanged: X:" + event.values[0] + " Y:" + event.values[1] + " Z:" + event.values[2]);
            xValue.setText("xValue: " + event.values[0]);
            yValue.setText("yValue: " + event.values[1]);
            zValue.setText("zValue: " + event.values[2]);
            if((event.values[0] >=2 && event.values[0]<= 8)|| (event.values[1]<= 8)|| (event.values[2] >=4 && event.values[2]<= 8)){
                notification.start();
            }
        } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE){
            Log.d(TAG, "onSensorChanged: Xgyro:" + event.values[0]+ " YGyro:" + event.values[1] + " ZGyro:" + event.values[2]);
            xGyroValue.setText("xGyroValue: " + event.values[0]);
            yGyroValue.setText("yGyroValue: " + event.values[1]);
            zGyroValue.setText("zGyroValue: " + event.values[2]);

        }

    }
}
