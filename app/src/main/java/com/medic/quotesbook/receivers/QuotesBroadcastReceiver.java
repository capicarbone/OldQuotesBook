package com.medic.quotesbook.receivers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.medic.quotesbook.services.GlueQuotesService;

/**
 * Created by capi on 15/05/15.
 */
public class QuotesBroadcastReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "QuotesBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "EL broadcast se ha activado");

        ComponentName comp = new ComponentName(context.getPackageName(), GlueQuotesService.class.getName());
        intent.setComponent(comp);

        startWakefulService(context, intent);
        setResultCode(Activity.RESULT_OK);


    }
}
