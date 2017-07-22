package com.medic.quotesbook;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.services.WidgetQuotesReaderService;
import com.medic.quotesbook.utils.QuotesStorage;
import com.medic.quotesbook.views.activities.QuoteActivity;

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

        if (quotes.length > 1){

            Quote q = quotes[0];
            qs.removeQuote(q.getKey());
            q = quotes[1];
            qs.commit();

            showQuoteInWidget(q, context, views, appWidgetManager, appWidgetIds);
        }

        if (quotes.length <= 5){
            Intent i = new Intent(context, WidgetQuotesReaderService.class);
            i.putExtra(WidgetQuotesReaderService.PARAM_WIDGETS_IDS, appWidgetIds);
            context.startService(i);
        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

        QuotesStorage qs = new QuotesStorage(WIDGET_QUOTES_FILE, context);
        qs.clear();
        qs.commit();
    }

    public static void showQuoteInWidget(Quote quote, Context context, RemoteViews views, AppWidgetManager awm, int[] appWidgetIds){

        //quote.setBody("Once conform, once do what other people do because they do it, and a lethargy steals over all the finer nerves and faculties of the soul. She becomes all outer show and inward emptiness; dull, callous, and indifferent.");
        //quote.setBody("Be faithful to that which exists within yourself.");
        //quote.setBody("No matter what age you are, or what your circumstances might be, you are special, and you still have something unique to offer. Your life, because of who you are, has meaning.");
        //quote.setBody("The real voyage of discovery consists not in seeking new landscapes, but in having new eyes.");

        views.setTextViewText(R.id.quote_text, quote.getBody());
        views.setTextViewText(R.id.quote_author, "- " + quote.getAuthor().getFullName());

        if (quote.getBody().length() >= 200){
            changeQuoteTextSize(views, 16);
        } else if (quote.getBody().length() >= 150){
            changeQuoteTextSize(views, 18);
        }

        if (quote.getBody().length() <= 55){
            changeQuoteTextSize(views, 30);
        }else if (quote.getBody().length() < 90){
            changeQuoteTextSize(views, 25);
        }

        Intent quoteActivityIntent = new Intent(context, QuoteActivity.class);
        quoteActivityIntent.putExtra(QuoteActivity.QUOTE_KEY, quote);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 20323, quoteActivityIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.root, pendingIntent);

        for (int i = 0; i < appWidgetIds.length ; i++){
            awm.updateAppWidget(appWidgetIds[i], views);
        }

    }

    public static void changeQuoteTextSize(RemoteViews views, int size){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            views.setTextViewTextSize(R.id.quote_text, TypedValue.COMPLEX_UNIT_SP, size);
        }else{
            views.setFloat(R.id.quote_text, "setTextSize", size);
        }
    }
}
