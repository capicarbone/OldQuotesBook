package com.medic.quotesbook.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.services.GlueQuotesService;

/**
 * Created by capi on 25/07/15.
 */
public class DaysQuoteManager {

    private Gson gson;
    private SharedPreferences sp;
    private String quotesRaw;

    private Quote[] quotes;

    public DaysQuoteManager(Context ctx){

        sp = ctx.getSharedPreferences(GlueQuotesService.QUOTES_QUEUE_FILE, ctx.MODE_PRIVATE);
        quotesRaw = sp.getString("quotes", null);

        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        quotes = gson.fromJson(quotesRaw, Quote[].class);

    }

    public Quote getTodayQuote(){

        Quote todayQuote = null;

        if (quotes != null && quotes.length > 0){
            todayQuote = quotes[0];
        }

        return todayQuote;

    }

    public Quote getNextQuote(){

        Quote nextQuote = null;

        if (quotes != null && quotes.length > 0){

            Quote[] newQueue = new Quote[quotes.length-1];

            for (int i = newQueue.length; i > 0; i-- ){

                newQueue[i-1] = quotes[i];
            }

            quotes = newQueue;

            updateQuotesList();

            nextQuote = quotes[0];
        }

        return nextQuote;

    }

    public void updateQuotesList(){

        quotesRaw = gson.toJson(quotes, Quote[].class);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("quotes", quotesRaw);

        editor.commit();

    }

    public void changeQuotesList(String fromServer){

        Quote[] newQuotes = gson.fromJson(fromServer, Quote[].class);

        Quote todayQuote = quotes[0];

        newQuotes[0] = todayQuote;

        quotes = newQuotes;

        updateQuotesList();

    }


}
