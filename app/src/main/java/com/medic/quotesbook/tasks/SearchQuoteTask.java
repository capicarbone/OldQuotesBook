package com.medic.quotesbook.tasks;

import android.view.View;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesSearchRequestMsg;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesSearchResultMsg;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.medic.quotesbook.views.adapters.QuotesAdapter;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by capi on 10/12/15.
 */
public class SearchQuoteTask extends QuotesFromServerTask {

    ApiMessagesSearchResultMsg searchResult;


    public SearchQuoteTask(QuotesAdapter a, View loaderLayout, View mainLayout, View exceptionLayout) {
        super(a, loaderLayout, mainLayout, exceptionLayout);
    }

    public SearchQuoteTask(){
        super();
    }

    @Override
    public int getSourceType() {
        return SOURCETYPE_SERVER;
    }

    @Override
    public void updateListState(QuotesListState listState) {

        listState.totalItemsWaited = searchResult.getTotalResults();
        listState.itemsReceived = searchResult.getPageQuotes().size();

    }


    @Override
    protected List<ApiMessagesQuoteMsg> getQuotesRequest(Quotesclient service, QuotesListState listState) throws IOException, UnknownHostException, GoogleJsonResponseException {

        ApiMessagesSearchRequestMsg requestMsg = new ApiMessagesSearchRequestMsg();
        ApiMessagesSearchResultMsg resultMsg = null;

        int nextPage = 0;
        int totalPages;

        if (listState.totalItemsWaited > 0){
            totalPages = (int) (listState.totalItemsWaited / listState.pageSize);

            if (listState.totalItemsWaited % listState.pageSize > 0){
                totalPages = totalPages + 1;
            }

            nextPage = totalPages - (listState.itemsReceived / listState.pageSize) ;
        }

        requestMsg.setPageSize((long) listState.pageSize);
        requestMsg.setNPage((long) nextPage);
        requestMsg.setTextQuery(getQuery());

        resultMsg = service.quotes().search(requestMsg).execute();

        searchResult = resultMsg;

        return resultMsg.getPageQuotes();

    }

}
