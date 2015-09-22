package com.medic.quotesbook.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.TodayQuoteManager;
import com.medic.quotesbook.views.activities.QuoteActivity;

import java.io.IOException;
import java.net.URL;

/**
 * Created by capi on 27/05/15.
 *
 * Servicio que prepara la cita del día para ser notificada al usuario.
 */
public class PrepareDaysQuoteService extends IntentService {

    private final String TAG = "PrepareDaysQuoteService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public PrepareDaysQuoteService() {
        super("PrepareDaysQuoteService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        TodayQuoteManager quotesManager = new TodayQuoteManager(this.getBaseContext());

        Quote quote = quotesManager.getNextQuote();

        if (quote != null)
            giveQuote(quote, this.getBaseContext());
    }


    private Bitmap getAuthorImage(String imageUrl){

        Bitmap image = null;

        try {
            URL url = new URL(imageUrl);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    private void giveQuote(Quote quote, Context ctx){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);


        builder.setContentText("Un mensaje de " + quote.getAuthor().getFullName())
                .setContentTitle("Alguien tiene algo que decirte")
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);


        if (quote.getAuthor() != null){
            Bitmap authorImage = getAuthorImage("http://quotesbookapp.appspot.com/" + quote.getAuthor().getPictureUrl());

            builder.setLargeIcon(authorImage);
        }

        Intent intent = new Intent(ctx, QuoteActivity.class);
        intent.putExtra(QuoteActivity.QUOTE_KEY, quote);
        intent.putExtra(QuoteActivity.DAYQUOTE_KEY, true);

        /*
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(BaseActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        */

        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(getBaseContext(), notification);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5, builder.build());

        ringtone.play();
    }

}
