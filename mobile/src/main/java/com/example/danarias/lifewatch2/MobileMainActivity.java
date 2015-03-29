package com.example.danarias.lifewatch2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class MobileMainActivity extends ActionBarActivity implements OnClickListener{

    Button emergencyButton;
    Button contactsButton;
    Button medReminderButton;
    Button settingsButton;

    ImageView questionMark;

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

        questionMark = (ImageView) findViewById(R.id.aboutImageView);


        questionMark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MobileMainActivity.this, AboutAppActivity.class);
                startActivity(intent);
                finish();

            }

        });


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

            case R.id.contactsButton:
                Intent intent2 = new Intent(MobileMainActivity.this, ContactsActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.medButton:
                Intent intent3 = new Intent(MobileMainActivity.this, MedReminderActivity.class);
                startActivity(intent3);
                finish();
                break;
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
