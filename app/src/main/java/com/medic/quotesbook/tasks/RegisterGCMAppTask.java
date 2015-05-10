package com.medic.quotesbook.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.medic.quotesbook.R;

import java.io.IOException;

/**
 * Created by capi on 10/05/15.
 */
public class RegisterGCMAppTask extends AsyncTask<Context, Void, Boolean> {

    Context ctx;
    GoogleCloudMessaging gcm;

    public RegisterGCMAppTask(GoogleCloudMessaging gcm) {
        this.gcm = gcm;
    }

    @Override
    protected Boolean doInBackground(Context... params) {

        ctx = params[0];
        String senderId = ctx.getString(R.string.SENDER_ID);

        String regid;

        try{
            regid = gcm.register(senderId);

        }catch (IOException ex){

        }

        return true;
    }
}
