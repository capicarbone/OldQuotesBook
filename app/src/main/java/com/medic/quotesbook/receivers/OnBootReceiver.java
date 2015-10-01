package com.medic.quotesbook.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.medic.quotesbook.utils.DevUtils;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * Created by capi on 24/05/15.
 *
 * Broadcast que coloca la alarma para la cita del día.
 *
 */
public class OnBootReceiver extends BroadcastReceiver {

    private static final String TAG = "OnBootReeiver";

    private Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.ctx = context;

        setQuoteTimeAlarm();

        DevUtils.showNotification("Alarma colocada", ctx);
    }


    private void setQuoteTimeAlarm(){

        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(ctx, QuoteTimeReceiver.class);

        PendingIntent quoteTimeIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);

        //Calendar time = Calendar.getInstance();
        //time.setTimeInMillis(System.currentTimeMillis());
        //time.set(Calendar.HOUR_OF_DAY, 8);
        //time.set(Calendar.MINUTE, 30);

        DateTime nextAlarm = new DateTime(System.currentTimeMillis()).withHourOfDay(8);
        nextAlarm = nextAlarm.withMinuteOfHour(30);

        if (nextAlarm.isBeforeNow())
            nextAlarm.plusDays(1);

        DateTime testAlarm = new DateTime(System.currentTimeMillis());
        testAlarm.plusMinutes(1);

        am.setInexactRepeating(AlarmManager.RTC, nextAlarm.getMillis(), AlarmManager.INTERVAL_HALF_DAY, quoteTimeIntent);

        //am.setInexactRepeating(AlarmManager.RTC, testAlarm.getMillis(), 18000L, quoteTimeIntent);



    }
}
