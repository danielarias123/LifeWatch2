package com.example.danarias.lifewatch2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.os.CountDownTimer;

import java.util.List;
import java.util.concurrent.TimeUnit;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.telephony.SmsManager;



public class EmergActivity extends ActionBarActivity implements OnClickListener{

    Button cancEmergButton;
    Button callNowButton;

    private DbHelper myDb = new DbHelper(EmergActivity.this);


    TextView countdown;
    private static final String FORMAT = "%02d";
    public String setTime = SettingsActivity.waitTime;
    CountDownTimer timer;



    long seconds = 6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emerg);

        cancEmergButton = (Button) findViewById(R.id.cancEmergButton);
        cancEmergButton.setOnClickListener(this);

        callNowButton = (Button) findViewById(R.id.contactNowButton);
        callNowButton.setOnClickListener(this);

        countdown=(TextView)findViewById(R.id.countdownTextView);




        if(setTime.equals("5 s")){
            seconds = 6000;
        }


        if(setTime.equals("10 s")){
            seconds = 11000;
        }

        if(setTime.equals("15 s")){
            seconds = 16000;
        }

        if(setTime.equals("30 s")){
            seconds = 31000;
        }

        if(setTime.equals("60 s")){
            seconds = 61000;
        }


        timer = new CountDownTimer(seconds,100){

            public void onTick(long millisUntilFinished) {

                countdown.setText(""+String.format(FORMAT,

                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                countdown.setText("SENT");

                String textSMS = "** LifeWatch Automated Message ** Help! I have fallen and can't get up.";

                List<String> recipients = myDb.getAllNumbers();
                for (String PhoneNo: recipients ) {
                    if (!PhoneNo.equals("")) {
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(PhoneNo, null, textSMS, null, null);
                            Toast.makeText(getApplicationContext(), "Emergency SMS Sent to "+PhoneNo+"!",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }

                }




            }
        }.start();


    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.cancEmergButton:
                Intent intent = new Intent(EmergActivity.this, MobileMainActivity.class);
                timer.cancel();
                Toast.makeText(getApplicationContext(), "Emergency Cancelled!",
                        Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
                break;


            case R.id.contactNowButton:
                timer.cancel();
                countdown.setText("SENT");

                
                String textSMS = "** LifeWatch Automated Message ** Help! I have fallen and can't get up.";

                List<String> recipients = myDb.getAllNumbers();
                for (String PhoneNo: recipients ) {
                    if (!PhoneNo.equals("")) {
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(PhoneNo, null, textSMS, null, null);
                            Toast.makeText(getApplicationContext(), "Emergency SMS Sent to "+PhoneNo+"!",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }

                }
                countdown.setFreezesText(true);
                // Intent i = new Intent(MobileMainActivity.this, ContactsActivity.class);
                // startActivity(i);
                // finish();
                break;

                //     case R.id.settingsButton:
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emerg, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();

        Intent i = new Intent(EmergActivity.this, MobileMainActivity.class);
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