package com.medic.quotesbook.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.medic.quotesbook.models.Quote;

import java.util.List;
import java.util.Set;

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

        sendNotification("Nos llegó un mensaje");

        Bundle extras = intent.getExtras();

        Gson gson = new Gson();

        Quote[] quotes = gson.fromJson(extras.getString("quotes"), Quote[].class);

        Log.d(TAG, "Todo bien" );

    }

    private void sendNotification(String msg){

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Tenemos algo")
                .setContentText(msg);

        manager.notify(1, builder.build());

    }

}
