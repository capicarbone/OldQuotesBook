package com.medic.quotesbook.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection;
import com.medic.quotesbook.SingleQuoteWidgetProvider;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.QuoteNetwork;
import com.medic.quotesbook.utils.QuotesStorage;

import java.io.IOException;

/**
 * Created by capi on 7/21/17.
 */

public class WidgetQuotesReaderService extends IntentService{
    public WidgetQuotesReaderService() {
        super("WidgetQuotesReaderService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Quotesclient service = QuoteNetwork.getQuotesService();
        QuotesStorage qs = new QuotesStorage(SingleQuoteWidgetProvider.WIDGET_QUOTES_FILE, getApplicationContext());

        qs.clear();

        try {
            ApiMessagesQuotesCollection response = service.quotes().some().setLimit(20).execute();

            Log.d(getClass().getSimpleName(), "Se reciben frases");

            for (ApiMessagesQuoteMsg apiQuote : response.getQuotes()){

                Quote quote = new Quote(apiQuote);
                qs.addQuoteTop(quote);
                qs.commit();
            }

        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), e.getMessage(), e);
        }
    }
}
