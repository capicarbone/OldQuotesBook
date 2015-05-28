package com.medic.quotesbook.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.QuoteNetwork;
import com.medic.quotesbook.views.adapters.QuotesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by capi on 25/01/15.
 */
public class GetSomeQuotesTask extends AsyncTask<Integer, String,ArrayList<Quote>> {

    QuotesAdapter mAdapter;

    public GetSomeQuotesTask(QuotesAdapter a) {
        mAdapter = a;
    }

    @Override
    protected ArrayList<Quote> doInBackground(Integer... limit) {

        Quotesclient service = QuoteNetwork.getQuotesService();
        ArrayList<Quote> quotes = new ArrayList<Quote>();

        ApiMessagesQuotesCollection response = null;

        try {
            response = service.quotes().some().setLimit(12).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {

            /*try {
                Log.d("QuotesBook", response.toPrettyString());
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            for (Iterator<ApiMessagesQuoteMsg> iter = response.getQuotes().iterator(); iter.hasNext();){
                ApiMessagesQuoteMsg quoteMsg= iter.next();

                quotes.add(new Quote(quoteMsg));
            }
        }

        return quotes;
    }

    @Override
    protected void onPostExecute(ArrayList<Quote> quotes) {

        mAdapter.quotes = quotes;
        mAdapter.notifyDataSetChanged();
    }
}
