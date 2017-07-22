package com.medic.quotesbook;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.services.WidgetQuotesReaderService;
import com.medic.quotesbook.utils.QuotesStorage;

/**
 * Created by capi on 7/19/17.
 */

public class SingleQuoteWidgetProvider extends AppWidgetProvider {

    public static final String WIDGET_QUOTES_FILE = "widget-quotes";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        QuotesStorage qs = new QuotesStorage(WIDGET_QUOTES_FILE, context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_single_quote);

        Log.d(getClass().getSimpleName(), "Se ejecuta el Widget Provider!!!!!");

        Quote[] quotes = qs.getQuotes();

        if (quotes.length > 0){

            Quote q = quotes[0];
            qs.removeQuote(q.getKey());
            q = quotes[1];
            qs.commit();

            showQuoteInWidget(q, views, appWidgetManager, appWidgetIds);
        }else{

            Intent i = new Intent(context, WidgetQuotesReaderService.class);
            context.startService(i);

            // TODO: Show firts widget in service
        }
    }

    public static void showQuoteInWidget(Quote quote, RemoteViews views, AppWidgetManager awm, int[] appWidgetIds){


        views.setTextViewText(R.id.quote_text, quote.getBody());
        views.setTextViewText(R.id.quote_author, "- " + quote.getAuthor().getFullName());

        awm.updateAppWidget(appWidgetIds[0], views);
    }
}
