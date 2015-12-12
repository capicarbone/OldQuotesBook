package com.medic.quotesbook.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medic.quotesbook.R;
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

    public GetQuotesbookTask(){
        super();
    }

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

        showException();

        TextView exceptionText = (TextView) getExceptionLayout().findViewById(R.id.exception_text);
        ImageView exceptionIcon = (ImageView) getExceptionLayout().findViewById(R.id.exception_icon);
        Button reloadButton = (Button) getExceptionLayout().findViewById(R.id.reload_button);

        reloadButton.setVisibility(View.GONE);

        exceptionText.setText(R.string.message_quotesbook_empty);
        exceptionIcon.setImageResource(R.drawable.ic_star_border_black_36dp);

    }

    @Override
    public int getSourceType() {
        return SOURCETYPE_LOCAL;
    }

    @Override
    public void updateListState(QuotesListState listState) {
        listState.totalItemsWaited = 0;
        listState.itemsReceived = 0;
    }
}
