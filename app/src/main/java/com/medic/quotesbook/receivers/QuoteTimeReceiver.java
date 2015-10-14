package com.medic.quotesbook.receivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.services.GlueQuotesService;
import com.medic.quotesbook.services.PrepareDaysQuoteService;
import com.medic.quotesbook.utils.DevUtils;

import org.joda.time.DateTime;

/**
 * Created by capi on 25/05/15.
 *
 * Broadcast activado cuando llega la hora de notificar la cita del día.
 */
public class QuoteTimeReceiver extends BroadcastReceiver {

    static private final String TAG = "QuoteTimeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, PrepareDaysQuoteService.class);
        context.startService(i);

        setQuoteTimeAlarm(context);
    }

    public static void setQuoteTimeAlarm(Context ctx){

        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(ctx, QuoteTimeReceiver.class);

        PendingIntent quoteTimeIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);

        //Calendar time = Calendar.getInstance();
        //time.setTimeInMillis(System.currentTimeMillis());
        //time.set(Calendar.HOUR_OF_DAY, 8);
        //time.set(Calendar.MINUTE, 30);

        DateTime nextAlarm = new DateTime();

        nextAlarm = nextAlarm.withHourOfDay(8)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0);

        if (nextAlarm.isBeforeNow())
            nextAlarm = nextAlarm.plusDays(1);


        am.set(AlarmManager.RTC, nextAlarm.getMillis(), quoteTimeIntent);

        // DateTime testAlarm = new DateTime(System.currentTimeMillis());
        //testAlarm = testAlarm.plusMinutes(1);

        //am.set(AlarmManager.RTC, testAlarm.getMillis(), quoteTimeIntent);

        //am.setInexactRepeating(AlarmManager.RTC, testAlarm.getMillis(), 18000L, quoteTimeIntent);



    }


}
