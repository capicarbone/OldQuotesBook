package com.medic.quotesbook.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by capi on 15/05/15.
 */
public class GlueQuotesService extends IntentService{

    public final String TAG = "GlueQuoteService";



    public GlueQuotesService() {
        super("GlueQuotesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();

        Log.d(TAG, extras.toString());

        sendNotification("Nos llegó un mensaje");
    }

    private void sendNotification(String msg){

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Tenemos algo")
                .setContentText(msg);

        manager.notify(1, builder.build());

    }

}
