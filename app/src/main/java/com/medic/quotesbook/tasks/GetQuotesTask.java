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

    private QuotesAdapter mAdapter;
    private View loaderLayout;
    private View mainLayout;
    private View exceptionLayout;
    private Context ctx;

    boolean loading;

    private int exceptionCode = 0;

    ArrayList<Quote> quotes = new ArrayList<Quote>();


    public GetQuotesTask(QuotesAdapter a, View loaderLayout, View mainLayout, View exceptionLayout) {
        mAdapter = a;
        this.loaderLayout = loaderLayout;
        this.mainLayout = mainLayout;
        this.exceptionLayout = exceptionLayout;

        loading = true;
    }

    public GetQuotesTask(QuotesAdapter mAdapter, View loaderLayout, View mainLayout, View exceptionLayout, Context ctx) {
        this.mAdapter = mAdapter;
        this.loaderLayout = loaderLayout;
        this.ctx = ctx;
        this.mainLayout = mainLayout;
        this.exceptionLayout = exceptionLayout;

        loading = true;

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

    public void setException(int connectionState){
        this.exceptionCode = connectionState;
    }

    public View getLoaderLayout() {
        return loaderLayout;
    }

    public View getMainLayout() {
        return mainLayout;
    }

    public View getExceptionLayout() {
        return exceptionLayout;
    }

    public boolean failedRequest(){
        return exceptionCode != 0;
    }

    @Override
    protected ArrayList<Quote> doInBackground(Integer... limit) {
        loading = true;

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Quote> quotes) {

        loading = false;

        if (exceptionCode == 0){
            if (mAdapter.quotes == null || mAdapter.quotes.size() == 0 ){
                mAdapter.quotes = quotes;

               showQuotesList();

            }else{
                mAdapter.quotes.addAll(quotes);

            }

            mAdapter.notifyDataSetChanged();

        }else{
            notifyException(exceptionCode);
        }

    }

    protected void showLoader(){
        loaderLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        exceptionLayout.setVisibility(View.GONE);
    }

    protected void showQuotesList(){
        loaderLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
        exceptionLayout.setVisibility(View.GONE);
    }

    protected void showException(){
        loaderLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);
        exceptionLayout.setVisibility(View.VISIBLE);
    }

    abstract protected void notifyException(int exceptionCode);


}
