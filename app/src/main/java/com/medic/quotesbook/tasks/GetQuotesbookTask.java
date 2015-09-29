package com.medic.quotesbook.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.QuotesStorage;
import com.medic.quotesbook.views.adapters.QuotesAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by capi on 24/08/15.
 */
public class GetQuotesbookTask extends GetQuotesTask {

    public static final int EXCEPTION_NO_QUOTES = 1;

    public GetQuotesbookTask(QuotesAdapter mAdapter, View loaderLayout, View mainLayout, View exceptionLayout, Context ctx) {
        super(mAdapter, loaderLayout, mainLayout, exceptionLayout, ctx );
    }

    @Override
    protected ArrayList<Quote> doInBackground(Integer... integers) {

        ArrayList<Quote> quotes = null;

        QuotesStorage qStorage = new QuotesStorage(QuotesStorage.QUOTESBOOK_FILE, getContext() );

        quotes = new ArrayList<Quote>(Arrays.asList(qStorage.getQuotes()));

        if (quotes.size() == 0)
            setException(EXCEPTION_NO_QUOTES);

        return quotes;

    }

    @Override
    protected void notifyException(int exceptionCode) {

        Log.d("QuotesbookTask", "No hay citas");
    }
}
