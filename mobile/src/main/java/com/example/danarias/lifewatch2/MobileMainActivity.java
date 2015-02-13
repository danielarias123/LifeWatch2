package com.example.danarias.lifewatch2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class MobileMainActivity extends ActionBarActivity implements OnClickListener{

    Button emergencyButton;
    Button contactsButton;
    Button medReminderButton;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_main);

        emergencyButton = (Button) findViewById(R.id.emergButton);
        emergencyButton.setOnClickListener(this);

        contactsButton = (Button) findViewById(R.id.contactsButton);
        contactsButton.setOnClickListener(this);

        medReminderButton = (Button) findViewById(R.id.medButton);
        medReminderButton.setOnClickListener(this);

        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this);

    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.emergButton:
                Intent intent = new Intent(MobileMainActivity.this, EmergActivity.class);
                startActivity(intent);
                finish();
                break;


            case R.id.settingsButton:
                Intent i = new Intent(MobileMainActivity.this, SettingsActivity.class);
                startActivity(i);
                finish();
                break;

       //     case R.id.settingsButton:
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mobile_main, menu);
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
