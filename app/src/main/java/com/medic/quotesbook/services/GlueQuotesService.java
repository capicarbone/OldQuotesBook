package com.medic.quotesbook.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.DaysQuoteManager;
import com.medic.quotesbook.utils.DevUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by capi on 15/05/15.
 */
public class GlueQuotesService extends IntentService{

    public final String TAG = "GlueQuoteService";

    public static final String QUOTES_QUEUE_FILE = "quotes_queue";

    public GlueQuotesService() {
        super("GlueQuotesService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        //DevUtils.showNotification("Llegaron citas", this.getBaseContext());

        Bundle extras = intent.getExtras();

        String rawQuotes = extras.getString("quotes");

        if ( rawQuotes != null){

            DaysQuoteManager qManager = new DaysQuoteManager(getBaseContext());
            qManager.changeQuotesList(rawQuotes);

        }

    }

}
