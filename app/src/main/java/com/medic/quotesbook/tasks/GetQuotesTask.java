package com.medic.quotesbook.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.views.adapters.QuotesAdapter;
import com.medic.quotesbook.views.fragments.QuotesListFragment;

import java.util.ArrayList;

/**
 * Created by capi on 24/08/15.
 */
public abstract class GetQuotesTask extends AsyncTask<Integer, String,ArrayList<Quote>> {

    public static final int SOURCETYPE_SERVER = 1;
    public static final int SOURCETYPE_LOCAL = 2;

    private QuotesAdapter adapter;
    private View loaderLayout;
    private View mainLayout;
    private View exceptionLayout;
    private Context ctx;

    boolean loading;

    private int exceptionCode = 0;

    ArrayList<Quote> quotes = new ArrayList<Quote>();

    private QuotesListState listState;


    public GetQuotesTask(QuotesAdapter a, View loaderLayout, View mainLayout, View exceptionLayout) {
        adapter = a;
        this.loaderLayout = loaderLayout;
        this.mainLayout = mainLayout;
        this.exceptionLayout = exceptionLayout;

        loading = true;
    }

    public GetQuotesTask(QuotesAdapter adapter, View loaderLayout, View mainLayout, View exceptionLayout, Context ctx) {
        this.adapter = adapter;
        this.loaderLayout = loaderLayout;
        this.ctx = ctx;
        this.mainLayout = mainLayout;
        this.exceptionLayout = exceptionLayout;

        loading = true;

    }

    public QuotesListState getListState() {
        return listState;
    }

    public void setListState(QuotesListState listState) {
        this.listState = listState;
    }

    public GetQuotesTask(){}

    public void setLoaderLayout(View loaderLayout) {
        this.loaderLayout = loaderLayout;
    }

    public void setMainLayout(View mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void setExceptionLayout(View exceptionLayout) {
        this.exceptionLayout = exceptionLayout;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public void setListAdapter(QuotesAdapter mAdapter) {
        this.adapter = mAdapter;

        if ( getSourceType() == SOURCETYPE_LOCAL && adapter != null && adapter.quotes != null){

            int itemsCount = adapter.quotes.size();

            adapter.quotes.clear();
            adapter.notifyItemRangeRemoved(0, itemsCount);
        }
    }

    public GetQuotesTask(QuotesAdapter adapter) {
        this.adapter = adapter;
    }

    public QuotesAdapter getAdapter() {
        return adapter;
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

            int insertionPosition = 0;

            updateListState(listState);

            if (!listState.isNextPage())
                adapter.removeLoader();

            if (adapter.quotes == null || adapter.quotes.size() == 0 ){
                adapter.quotes = quotes;

               showQuotesList();

            }else{

                insertionPosition = adapter.quotes.size();

                adapter.quotes.addAll(quotes);

            }

            adapter.notifyItemRangeInserted(insertionPosition, quotes.size());

        }else{
            notifyException(exceptionCode);
        }

    }

    protected void showLoader(){
        loaderLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        exceptionLayout.setVisibility(View.GONE);
    }

    public void showQuotesList(){
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
    abstract public int getSourceType();
    abstract public void updateListState(QuotesListState listState);

    // InnerClass

    public static class QuotesListState{

        public long totalItemsWaited = -1;
        public int pageSize = 12;
        public int itemsRequested = 0;
        public int itemsReceived = 0;

        public boolean isNextPage(){
            if (totalItemsWaited == -1)
                return true;
            else
                return itemsReceived < totalItemsWaited;
        }

    }

}
