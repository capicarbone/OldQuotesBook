package com.medic.quotesbook.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.services.GlueQuotesService;

/**
 * Created by capi on 22/08/15.
 */
public class QuotesStorage {

    public static final String QUOTESBOOK_FILE = "quotesbook";

    static protected final String KEY_QUOTES_LIST = "quotes";

    private Gson gson;
    private SharedPreferences sp;
    private String quotesRaw;

    Quote[] quotes;

    public QuotesStorage(String spFileName, Context ctx){

        sp = ctx.getSharedPreferences(spFileName, ctx.MODE_PRIVATE);
        quotesRaw = sp.getString(KEY_QUOTES_LIST, null);

        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        quotes = gson.fromJson(quotesRaw, Quote[].class);

        if (quotes == null){
            quotes = new Quote[0];
        }
    }

    public void commit(){

        quotesRaw = gson.toJson(quotes, Quote[].class);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString(KEY_QUOTES_LIST, quotesRaw);

        editor.commit();

    }

    public void addQuoteTop(Quote quote){

        Quote[] newArray = new Quote[quotes.length + 1];

        newArray[0] = quote;

        for (int i = newArray.length - 1; i > 0; i--){

            newArray[i] = quotes[i-1];
        }

        quotes = newArray;

    }

    public void clear(){
        for (Quote quote: getQuotes()){
            removeQuote(quote.getKey());
        }
    }

    public Quote removeQuote(String quoteKey){

        Quote removedQuote = null;
        Quote[] newArray = new Quote[quotes.length - 1];

        Quote q = null;

        int j = 0;

        for (int i = 0; i <= newArray.length; i++){

            q = quotes[i];

            if ( !quoteKey.equals(q.getKey())){

                newArray[j] = quotes[i];
                j++;
            }else{
                removedQuote = quotes[i];
            }
        }

        quotes = newArray;

        return removedQuote;

    }

    public Quote[] getQuotes(){
        return quotes;
    }

    public String[] getKeys(){

        String[] keys = new String[quotes.length];

        for (int i = quotes.length; i >= 0; i--)
            keys[i] = quotes[i].getKey();

        return keys;
    }

    public int findQuote(String key){

        int index = -1;

        for(int i = quotes.length - 1; i >= 0; i--){

            if (quotes[i].getKey().equals(key)){
                return i;
            }
        }

        return index;
    }
}
