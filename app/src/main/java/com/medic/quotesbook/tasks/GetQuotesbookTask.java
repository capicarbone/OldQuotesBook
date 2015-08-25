package com.medic.quotesbook.tasks;

import android.content.Context;
import android.os.AsyncTask;
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

    public GetQuotesbookTask(QuotesAdapter mAdapter, View loaderLayout, View mainLayout, Context ctx) {
        super(mAdapter, loaderLayout, mainLayout, ctx);
    }

    @Override
    protected ArrayList<Quote> doInBackground(Integer... integers) {

        ArrayList<Quote> quotes = null;

        QuotesStorage qStorage = new QuotesStorage(QuotesStorage.QUOTESBOOK_FILE, getContext() );

        quotes = new ArrayList<Quote>(Arrays.asList(qStorage.getQuotes()));

        return quotes;

    }
}
