package com.medic.quotesbook.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.medic.quotesbook.R;

/**
 * Created by capi on 25/05/15.
 */
public class DevUtils  {

    public static void showNotification(String message, Context ctx){

        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Devs News")
                .setContentText(message);

        manager.notify(1, builder.build());

    }
}
