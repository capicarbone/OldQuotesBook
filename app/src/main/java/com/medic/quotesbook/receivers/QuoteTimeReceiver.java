package com.medic.quotesbook.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.services.GlueQuotesService;
import com.medic.quotesbook.services.PrepareDaysQuoteService;
import com.medic.quotesbook.utils.DevUtils;

/**
 * Created by capi on 25/05/15.
 */
public class QuoteTimeReceiver extends BroadcastReceiver {

    private final String TAG = "QuoteTimeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, PrepareDaysQuoteService.class);
        context.startService(i);


    }


}
