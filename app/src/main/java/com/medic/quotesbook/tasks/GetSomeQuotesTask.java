package com.medic.quotesbook.tasks;

import android.view.View;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection;
import com.medic.quotesbook.views.adapters.QuotesAdapter;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by capi on 25/01/15.
 */
public class GetSomeQuotesTask extends QuotesFromServerTask {

    public GetSomeQuotesTask(){
        super();
    }

    public GetSomeQuotesTask(QuotesAdapter a, View loaderLayout, View mainLayout, View exceptionLayout) {
        super(a, loaderLayout, mainLayout, exceptionLayout);
    }

    public GetSomeQuotesTask(QuotesAdapter adapter){
        super(adapter);
    }

    @Override
    public int getSourceType() {
        return SOURCETYPE_SERVER;
    }

    @Override
    protected List<ApiMessagesQuoteMsg> getQuotesRequest(Quotesclient service, QuotesListState listState) throws IOException, UnknownHostException {

        ApiMessagesQuotesCollection response = service.quotes().some().setLimit(DEFAULT_PAGE_SIZE).execute();

        return response.getQuotes();

    }

    @Override
    public void updateListState(QuotesListState listState){
        listState.totalItemsWaited = -1;

    }


}
