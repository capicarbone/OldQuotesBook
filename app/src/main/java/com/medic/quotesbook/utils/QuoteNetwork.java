package com.medic.quotesbook.utils;

import android.util.Log;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Locale;

/**
 * Created by capi on 25/01/15.
 *
 * Métodos utilitarios correspondientes a red.
 */
public class QuoteNetwork {

    private static Quotesclient service = null;

    private static final String ROOT_URL_ES = "https://quotesbookapp.appspot.com/";
    private static final String ROOT_URL_EN = "https://quotesbookappen.appspot.com/";

    public static Quotesclient getQuotesService(){

        if (service == null){
            Quotesclient.Builder builder = new Quotesclient.Builder(AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    null );

            String rootUrl = getRootURLByLocaleLanguage();
            //String rootUrl = "https://quotesbookapp.appspot.com/";

            //builder.setRootUrl("https://beta-dot-quotesbookapp.appspot.com/_ah/api/");
            builder.setRootUrl(rootUrl +  "_ah/api/");

            service = builder.build();
        }

        return service;
    }

    public static String getRootURLByLocaleLanguage(){

        String language = Locale.getDefault().getLanguage();

        if (language.equals("es")){
            return ROOT_URL_ES;
        }else
            return ROOT_URL_EN;

    }
}
