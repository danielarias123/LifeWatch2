package com.example.danarias.lifewatch2;
import android.app.Fragment;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class TestActivity extends ActionBarActivity implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager;
    private Timer timer[] = new Timer[4];

    boolean fall = false;

    TextView xCoor;
    TextView yCoor;
    TextView zCoor;
    TextView tCoor;
    TextView vMax;
    TextView vMin;
    TextView fDiff;
    TextView dMax;
    TextView dec;
    double t; // Magnitude
    double maxAcc[] = {0, 0, 0, 0};
    double minAcc[] = {Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE,Double.MAX_VALUE};
    double diff;
    double maxDiff;
    double min = Double.MAX_VALUE;
    double max = 0;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(this);

        xCoor=(TextView)findViewById(R.id.xcoor); // create X axis object
        yCoor=(TextView)findViewById(R.id.ycoor); // create Y axis object
        zCoor=(TextView)findViewById(R.id.zcoor); // create Z axis object
        tCoor=(TextView)findViewById(R.id.tcoor);
        vMax=(TextView)findViewById(R.id.vmax);
        vMin=(TextView)findViewById(R.id.vmin);
        fDiff=(TextView)findViewById(R.id.fdiff);
        dMax=(TextView)findViewById(R.id.maxdiff);
        dec=(TextView)findViewById(R.id.fallDec);
        dec.setText("Fall: " + fall);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        // add listener. The listener will be HelloAndroid (this) class
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        // init
        for (int i = 0; i < 4; i++) {
            timer[i] = new Timer();
            maxAcc[i] = 0;
            minAcc[i] = Double.MAX_VALUE;
        }

        timer[0].schedule(new TimerTask() {
            @Override
            public void run() {
                diff = Math.abs(maxAcc[0] - minAcc[0]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
                if (((diff) > 9.8) && minAcc[0] < 4)
                    fall = true;
                maxAcc[0] = 0;
                minAcc[0] = Double.MAX_VALUE;
            }
        } , 0, 1000);
        timer[1].schedule(new TimerTask() {
            @Override
            public void run() {
                diff = Math.abs(maxAcc[1] - minAcc[1]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
                if (((diff) > 9.8)  && minAcc[1] < 4)
                    fall = true;
                maxAcc[1] = 0;
                minAcc[1] = Double.MAX_VALUE;
            }
        } , 250, 1000);
        timer[2].schedule(new TimerTask() {
            @Override
            public void run() {
                diff = Math.abs(maxAcc[2] - minAcc[2]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
                if (((diff) > 9.8)  && minAcc[2] < 4)
                    fall = true;
                maxAcc[2] = 0;
                minAcc[2] = Double.MAX_VALUE;
            }
        } , 500, 1000);
        timer[3].schedule(new TimerTask() {
            @Override
            public void run() {
                diff = Math.abs(maxAcc[3] - minAcc[3]);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
                if (((diff) > 9.8)  && minAcc[3] < 4)
                    fall = true;
                maxAcc[3] = 0;
                minAcc[3] = Double.MAX_VALUE;
            }
        } , 750, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*try {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    sample = t;
                }
            };
            timer.schedule(timerTask, 0, 100);
        }
        catch (IllegalStateException e){
            android.util.Log.i("Resume: ", "resume error");
        }*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reset:
                for (int i = 0; i < 4; i++) {
                    maxAcc[i] = 0;
                    minAcc[i] = Double.MAX_VALUE;
                }
                max = 0;
                min = Double.MAX_VALUE;
                diff = 0;
                maxDiff = 0;
                fall = false;
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {

            // assign directions
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            t = Math.sqrt(x*x + y*y + z*z);
            for (int i = 0; i < 4; i++) {
                if (t > maxAcc[i])
                    maxAcc[i] = t;
                if (t < minAcc[i])
                    minAcc[i] = t;
            }

            if (t > max)
                max = t;
            if (t < min)
                min = t;

            xCoor.setText("X: " + x);
            yCoor.setText("Y: " + y);
            zCoor.setText("Z: " + z);
            tCoor.setText("Magnitude: " + t);

            fDiff.setText("Difference: " + diff);
            dMax.setText("Max difference: " + maxDiff);

            vMax.setText("Max: " + max);
            vMin.setText("Min: " + min);

            dec.setText("Fall: " + fall);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(TestActivity.this, SettingsActivity.class);
        startActivity(i);
        finish();
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
