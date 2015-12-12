package com.medic.quotesbook.tasks;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuoteMsg;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.QuoteNetwork;
import com.medic.quotesbook.views.adapters.QuotesAdapter;
import com.medic.quotesbook.views.fragments.QuotesListFragment;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by capi on 10/12/15.
 */
public abstract class QuotesFromServerTask extends GetQuotesTask {

    public static final int DEFAULT_PAGE_SIZE = 12;

    public static final int CONNECTION_DISABLED = 1;
    public static final int CONNECTION_BACKEND_ERROR = 2;

    private String query;
    private int pageNumber;
    private int pageSize;

    public QuotesFromServerTask(QuotesAdapter a, View loaderLayout, View mainLayout, View exceptionLayout) {
        super(a, loaderLayout, mainLayout, exceptionLayout);

        if (a != null || a.quotes != null || a.quotes.size() == 0){
            showLoader();
        }
    }

    public QuotesFromServerTask(QuotesAdapter adapter){
        super(adapter);
    }

    public QuotesFromServerTask(){
        super();
    }
// TODO: Rename and change types of parameters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    protected abstract List<ApiMessagesQuoteMsg> getQuotesRequest(Quotesclient service, QuotesListState listState) throws IOException, UnknownHostException, GoogleJsonResponseException;

    @Override
    protected ArrayList<Quote> doInBackground(Integer... limit) {
        super.doInBackground(limit);

        ArrayList<Quote> quotes = new ArrayList<Quote>();

        Quotesclient service = QuoteNetwork.getQuotesService();

        List<ApiMessagesQuoteMsg> quotesMsg = null;


        try {
            quotesMsg = getQuotesRequest(service, getListState());

        }
        catch (UnknownHostException e){
            setException(CONNECTION_DISABLED);

            Log.d("TAG", "No hay conexión");
        }
        catch (GoogleJsonResponseException e){
            setException(CONNECTION_BACKEND_ERROR);

            Log.d("TAG", "Error en el backend");
        }
        catch (IOException e) {
            setException(-1);
            e.printStackTrace();
        }

        if (quotesMsg != null) {

            /*try {
                Log.d("QuotesBook", response.toPrettyString());
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            for (Iterator<ApiMessagesQuoteMsg> iter = quotesMsg.iterator(); iter.hasNext();){
                ApiMessagesQuoteMsg quoteMsg= iter.next();

                quotes.add(new Quote(quoteMsg));
            }
        }

        return quotes;
    }

    @Override
    protected void notifyException(int exceptionCode){

        QuotesAdapter adapter = getAdapter();

        if (adapter == null || adapter.quotes == null || adapter.quotes.size() == 0){

            showException();

            TextView exceptionText = (TextView) getExceptionLayout().findViewById(R.id.exception_text);
            ImageView exceptionIcon = (ImageView) getExceptionLayout().findViewById(R.id.exception_icon);

            switch (exceptionCode){

                case CONNECTION_DISABLED:
                    exceptionText.setText(R.string.message_quotes_connection_error);
                    exceptionIcon.setImageResource(R.drawable.ic_signal_wifi_off_black_36dp);
                    break;
                case CONNECTION_BACKEND_ERROR:
                default:
                    exceptionIcon.setImageResource(R.drawable.ic_cloud_off_black_36dp);
                    exceptionText.setText(R.string.message_quotes_backend_error);
            }


        }

    }

}
