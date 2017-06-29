package com.example.danarias.lifewatch2;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.view.WatchViewStub;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableStatusCodes;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class WatchMainActivity extends Activity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks  {

    private TextView mTextView;
    private TextView ContactingTextView;

    private static final String WEAR_MESSAGE_PATH = "/message";
    private GoogleApiClient mApiClient;
    private ArrayAdapter<String> mAdapter;
    CountDownTimer timer;
    long seconds = 11000;
    TextView countdown;
    private static final String FORMAT = "%02d";
    private static final String START_ACTIVITY = "/EmergActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_watch_main);





        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks( this )
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();

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

        findViewById(R.id.cancImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExitClicked(v);
                finish();
            }
        });

        findViewById(R.id.nowImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNowClicked(v);
                finish();
            }
        });



    }



    @Override
    public void onMessageReceived( final MessageEvent messageEvent ) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (messageEvent.getPath().equalsIgnoreCase(WEAR_MESSAGE_PATH)) {
                    mAdapter.add(new String(messageEvent.getData()));
                    mAdapter.notifyDataSetChanged();
                }

                Intent intent = new Intent(WatchMainActivity.this, WatchMainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener(mApiClient, this);
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

    public void onExitClicked(View target) {
        finish();
        System.exit(0);
//        if (mApiClient == null)
//            return;
//
//        final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(mApiClient);
//        nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
//            @Override
//            public void onResult(NodeApi.GetConnectedNodesResult result) {
//                final List<Node> nodes = result.getNodes();
//                if (nodes != null) {
//                    for (int i = 0; i < nodes.size(); i++) {
//                        final Node node = nodes.get(i);
//
//                        // You can just send a message
//                        Wearable.MessageApi.sendMessage(mApiClient, node.getId(), "/MESSAGE", null);
//
//                        // or you may want to also check check for a result:
//                        // final PendingResult<SendMessageResult> pendingSendMessageResult = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), "/MESSAGE", null);
//                        // pendingSendMessageResult.setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
//                        //      public void onResult(SendMessageResult sendMessageResult) {
//                        //          if (sendMessageResult.getStatus().getStatusCode()==WearableStatusCodes.SUCCESS) {
//                        //              // do something is successed
//                        //          }
//                        //      }
//                        // });
//                    }
//                }
//            }
//        });
    }

    public void onNowClicked(View target) {

        if (mApiClient == null)
            return;

        final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(mApiClient);
        nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult result) {
                final List<Node> nodes = result.getNodes();
                if (nodes != null) {
                    for (int i = 0; i < nodes.size(); i++) {
                        final Node node = nodes.get(i);

                        // You can just send a message
                        Wearable.MessageApi.sendMessage(mApiClient, node.getId(), START_ACTIVITY, null);


                         final PendingResult<MessageApi.SendMessageResult> pendingSendMessageResult = Wearable.MessageApi.sendMessage(mApiClient, node.getId(), START_ACTIVITY, null);
                         pendingSendMessageResult.setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                              public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                  if (sendMessageResult.getStatus().getStatusCode()== WearableStatusCodes.SUCCESS) {


                                  }
                              }
                         });
                    }
                }
            }
        });
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
