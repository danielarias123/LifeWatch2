package com.example.danarias.lifewatch2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.os.CountDownTimer;
import java.util.concurrent.TimeUnit;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;


public class SettingsActivity extends ActionBarActivity implements OnClickListener, OnItemSelectedListener{

    Button backButton;




    public static String waitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        final CheckBox smartWatchFeatcheckBox = (CheckBox) findViewById(R.id.SmartWatchFeaturesCheckBox);
        if (smartWatchFeatcheckBox.isChecked()) {
            smartWatchFeatcheckBox.setChecked(false);
        }
        Spinner waitTimeSpinner = (Spinner) findViewById((R.id.waitTimeSpinner));
        ArrayAdapter<CharSequence> waitTimeAdapter = ArrayAdapter.createFromResource(this,R.array.wait_times,R.layout.spinner_layout);
        waitTimeAdapter.setDropDownViewResource(R.layout.spinner_layout);
        waitTimeSpinner.setAdapter(waitTimeAdapter);



        waitTimeSpinner.setOnItemSelectedListener(this);
    }



    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.backButton:
                Intent intent = new Intent(SettingsActivity.this, MobileMainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        waitTime = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + waitTime,
                Toast.LENGTH_LONG).show();

    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SettingsActivity.this, MobileMainActivity.class);
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
