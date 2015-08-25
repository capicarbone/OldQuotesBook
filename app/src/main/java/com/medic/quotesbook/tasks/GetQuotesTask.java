package com.medic.quotesbook.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.views.adapters.QuotesAdapter;

import java.util.ArrayList;

/**
 * Created by capi on 24/08/15.
 */
public abstract class GetQuotesTask extends AsyncTask<Integer, String,ArrayList<Quote>> {

    QuotesAdapter mAdapter;
    View loaderLayout;
    View mainLayout;
    Context ctx;

    boolean loading;

    ArrayList<Quote> quotes = new ArrayList<Quote>();


    public GetQuotesTask(QuotesAdapter a, View loaderLayout, View mainLayout) {
        mAdapter = a;
        this.loaderLayout = loaderLayout;
        this.mainLayout = mainLayout;
    }

    public GetQuotesTask(QuotesAdapter mAdapter, View loaderLayout, View mainLayout,  Context ctx) {
        this.mAdapter = mAdapter;
        this.loaderLayout = loaderLayout;
        this.ctx = ctx;
        this.mainLayout = mainLayout;
    }

    public GetQuotesTask(QuotesAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public QuotesAdapter getAdapter() {
        return mAdapter;
    }

    public Context getContext() {
        return ctx;
    }

    public boolean isLoading(){
        return loading;
    }

    @Override
    protected ArrayList<Quote> doInBackground(Integer... limit) {
        loading = true;

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Quote> quotes) {

        loading = false;

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
