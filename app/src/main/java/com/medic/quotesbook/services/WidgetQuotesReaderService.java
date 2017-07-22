package com.medic.quotesbook.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection;
import com.medic.quotesbook.R;
import com.medic.quotesbook.SingleQuoteWidgetProvider;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.QuoteNetwork;
import com.medic.quotesbook.utils.QuotesStorage;

import java.io.IOException;

/**
 * Created by capi on 7/21/17.
 */

public class WidgetQuotesReaderService extends IntentService{

    public static final String PARAM_WIDGETS_IDS = "widgets_ids";

    int[] widgetsIds;

    public WidgetQuotesReaderService() {
        super("WidgetQuotesReaderService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Quotesclient service = QuoteNetwork.getQuotesService();
        QuotesStorage qs = new QuotesStorage(SingleQuoteWidgetProvider.WIDGET_QUOTES_FILE, getApplicationContext());
        widgetsIds = intent.getIntArrayExtra(PARAM_WIDGETS_IDS);

        qs.clear();
        qs.commit();

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

        if (qs.getQuotes().length > 0){
            Quote actualQuote = qs.getQuotes()[0];


            RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_single_quote);

            AppWidgetManager awm = (AppWidgetManager) getApplicationContext().getSystemService(Context.APPWIDGET_SERVICE);

            SingleQuoteWidgetProvider.showQuoteInWidget(actualQuote, getApplicationContext(), views, awm, widgetsIds);
        }


    }
}
