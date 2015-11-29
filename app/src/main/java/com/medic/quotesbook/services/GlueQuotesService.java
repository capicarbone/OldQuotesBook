package com.medic.quotesbook.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.medic.quotesbook.receivers.QuoteTimeReceiver;
import com.medic.quotesbook.utils.DevUtils;
import com.medic.quotesbook.utils.TodayQuoteManager;

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

        Log.d(TAG, "Se activa el servicio de recepción de citas.");

        Bundle extras = intent.getExtras();

        String rawQuotes = extras.getString("quotes");

        if ( rawQuotes != null){

            TodayQuoteManager qManager = new TodayQuoteManager(getBaseContext());
            qManager.changeQuotesList(rawQuotes);

            Log.d(TAG, "Se actualizó el listado de citas.");

        }

        // TODO: Test for remove
        QuoteTimeReceiver.setQuoteTimeAlarm(getBaseContext());

    }

}
