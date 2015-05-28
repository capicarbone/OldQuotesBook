package com.medic.quotesbook.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;

/**
 * Created by capi on 27/05/15.
 */
public class PrepareDaysQuoteService extends IntentService {

    private final String TAG = "PrepareDaysQuoteService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public PrepareDaysQuoteService() {
        super("PrepareDaysQuoteService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Quote quote = unqueuQuote(this.getBaseContext());

        if (quote != null)
            giveQuote(quote, this.getBaseContext());
    }

    private Quote unqueuQuote(Context ctx){

        Quote nextQuote = null;

        SharedPreferences sp = ctx.getSharedPreferences(GlueQuotesService.QUOTES_QUEUE_FILE, ctx.MODE_PRIVATE);

        String quotesRaw = sp.getString("quotes", null);

        if (quotesRaw != null){

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            Quote[] quotes = gson.fromJson(quotesRaw, Quote[].class);

            if (quotes.length > 0 ){
                nextQuote = quotes[0];

                Quote[] newQueue = new Quote[quotes.length-1];

                for (int i = newQueue.length; i > 0; i-- ){

                    newQueue[i-1] = quotes[i];
                }

                quotesRaw = gson.toJson(newQueue, Quote[].class);

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("quotes", quotesRaw);

                editor.commit();
            }

        }

        return nextQuote;

    }

    private void giveQuote(Quote quote, Context ctx){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);

        builder.setContentTitle("Un mensaje de " + quote.getAuthor().getFullName())
                .setContentText("Alguien tiene algo que decirte")
                .setSmallIcon(R.drawable.ic_launcher);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5, builder.build());
    }
}
