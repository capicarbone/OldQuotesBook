package com.medic.quotesbook.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.analytics.CampaignTrackingReceiver;

public class InstallReferrerReceiver extends BroadcastReceiver {
    public InstallReferrerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if(intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
                //Tappx Track Install
                try {
                    com.tappx.TrackInstall tappx = new com.tappx.TrackInstall();
                    tappx.onReceive(context, intent);
                } catch (Exception p_ex) {
                    p_ex.printStackTrace();
                }

                // Analytics Receiver

                intent.setClass(context, CampaignTrackingReceiver.class);
                context.sendBroadcast(intent);

            }
        }
    }
}
