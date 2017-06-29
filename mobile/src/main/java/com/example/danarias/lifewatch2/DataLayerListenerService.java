package com.example.danarias.lifewatch2;

import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;

/**
 * Created by danarias on 15-04-24.
 */
public class DataLayerListenerService extends WearableListenerService {
    private static final String START_ACTIVITY = "/EmergActivity";



    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if(START_ACTIVITY.equals(messageEvent.getPath())) {
            Intent startIntent = new Intent(this, EmergActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);


        }
    }
}
