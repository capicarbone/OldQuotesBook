package com.medic.quotesbook.utils;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

/**
 * Created by capi on 25/01/15.
 *
 * Métodos utilitarios correspondientes a red.
 */
public class QuoteNetwork {

    public static Quotesclient getQuotesService(){

        Quotesclient.Builder builder = new Quotesclient.Builder(AndroidHttp.newCompatibleTransport(),
                new GsonFactory(),
                null );

        builder.setRootUrl("https://beta-dot-quotesbookapp.appspot.com/_ah/api/");

        return builder.build();
    }
}
