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

    private static final String TAG = "OnBootReceiver";

    private Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {

        QuoteTimeReceiver.setQuoteTimeAlarm(context);

    }

}
