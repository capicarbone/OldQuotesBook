package com.medic.quotesbook.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.services.GlueQuotesService;
import com.medic.quotesbook.utils.DevUtils;

/**
 * Created by capi on 25/05/15.
 */
public class QuoteTimeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Quote quote = unqueutQuote(context);

        DevUtils.showNotification("Tenemos una notificación", context);
    }

    private Quote unqueutQuote(Context ctx){

        Quote nextQuote = null;

        SharedPreferences sp = ctx.getSharedPreferences(GlueQuotesService.QUOTES_QUEUE_FILE, ctx.MODE_PRIVATE);

        String quotesRaw = sp.getString("quotes", null);

        if (quotesRaw != null){

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            Quote[] quotes = gson.fromJson(quotesRaw, Quote[].class);

            nextQuote = quotes[0];

            /*Quote[] newQueue = new Quote[quotes.length-1];

            for (int i = newQueue.length-1; i > 0; i-- ){

                newQueue[i] = quotes[i+1];
            }

            quotesRaw = gson.toJson(newQueue, Quote[].class);

            SharedPreferences.Editor editor = sp.edit();

            editor.putString("quotes", quotesRaw);

            editor.commit();*/


        }

        return nextQuote;


    }
}
