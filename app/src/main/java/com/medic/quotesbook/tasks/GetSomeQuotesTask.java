package com.medic.quotesbook.tasks;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
    View loaderLayout;
    View mainLayout;

    boolean loading;

    ArrayList<Quote> quotes = new ArrayList<Quote>();


    public GetSomeQuotesTask(QuotesAdapter a, View loaderLayout, View mainLayout) {
        mAdapter = a;
        this.loaderLayout = loaderLayout;
        this.mainLayout = mainLayout;
    }

    public GetSomeQuotesTask(QuotesAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public boolean isLoading(){
        return loading;
    }

    @Override
    protected ArrayList<Quote> doInBackground(Integer... limit) {

        loading = true;

        Quotesclient service = QuoteNetwork.getQuotesService();

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

        loading = false;

        return quotes;
    }

    @Override
    protected void onPostExecute(ArrayList<Quote> quotes) {

        if (mAdapter.quotes == null ){
            mAdapter.quotes = quotes;

            loaderLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);

            mAdapter.notifyDataSetChanged();
        }else{
            mAdapter.quotes.addAll(quotes);
        }

    }
}
