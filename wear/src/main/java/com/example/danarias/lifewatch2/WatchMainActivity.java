package com.example.danarias.lifewatch2;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.view.WatchViewStub;
import android.telephony.SmsManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class WatchMainActivity extends Activity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks  {

    private TextView mTextView;
    private TextView ContactingTextView;

    private static final String WEAR_MESSAGE_PATH = "/message";
    private GoogleApiClient mApiClient;
    private ArrayAdapter<String> mAdapter;
    CountDownTimer timer;
    long seconds = 6000;
    TextView countdown;
    private static final String FORMAT = "%02d";
    private static final String START_ACTIVITY = "/EmergActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_watch_main);

        initGoogleApiClient();
        countdown=(TextView)findViewById(R.id.watchcountdownTextView);

        timer = new CountDownTimer(seconds,100){

            public void onTick(long millisUntilFinished) {

                countdown.setText(""+String.format(FORMAT,

                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                countdown.setText("SENT");


            }
        }.start();



    }

    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks( this )
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }

    @Override
    public void onMessageReceived( final MessageEvent messageEvent ) {
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                if( messageEvent.getPath().equalsIgnoreCase( WEAR_MESSAGE_PATH ) ) {
                    mAdapter.add(new String(messageEvent.getData()));
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener( mApiClient, this );
    }

    @Override
    protected void onStop() {
        if ( mApiClient != null ) {
            Wearable.MessageApi.removeListener( mApiClient, this );
            if ( mApiClient.isConnected() ) {
                mApiClient.disconnect();
            }
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if( mApiClient != null )
            mApiClient.unregisterConnectionCallbacks( this );
        super.onDestroy();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }





}
