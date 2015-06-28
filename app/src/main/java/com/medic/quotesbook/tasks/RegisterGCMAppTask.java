package com.medic.quotesbook.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.appspot.quotesbookapp.quotesclient.model.ApiMessagesQuotesCollection;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.services.GlueQuotesService;
import com.medic.quotesbook.views.activities.BaseActivity;

import java.io.IOException;

/**
 * Created by capi on 10/05/15.
 */
public class RegisterGCMAppTask extends AsyncTask<Context, Void, Boolean> {

    private static final String TAG = "RegisterGCMAppTask";

    Context ctx;
    GoogleCloudMessaging gcm;

    public RegisterGCMAppTask(GoogleCloudMessaging gcm) {
        this.gcm = gcm;
    }

    @Override
    protected Boolean doInBackground(Context... params) {

        ctx = params[0];
        String senderId = ctx.getString(R.string.SENDER_ID);

        Quotesclient server = AppController.getInstance().getQuotesClient();

        String regid;

        try{
            regid = gcm.register(senderId);

            ApiMessagesQuotesCollection quotesMessage = server.gcmClient().save().set("gcm_id", regid).execute();

            Quote[] quotes = new Quote[quotesMessage.getQuotes().size()];

            for (int i = 0; i < quotesMessage.getQuotes().size() ; i++) {

                quotes[i] = new Quote(quotesMessage.getQuotes().get(i));

            }

            // Codigo tomado de PrepareDaysQuoteService, si es modificado alla debe modificarse aqui
            // TODO: Ver como colocar esto en un procedimiento


            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

            String quotesRaw = gson.toJson(quotes, Quote[].class);

            SharedPreferences sp = ctx.getSharedPreferences(GlueQuotesService.QUOTES_QUEUE_FILE, ctx.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("quotes", quotesRaw);

            editor.commit();


            // Save in Shared Preferences

            final SharedPreferences prefs = ctx.getSharedPreferences(ctx.getString(R.string.gcm_preferences), Context.MODE_PRIVATE);
            editor = prefs.edit();

            editor.putString(BaseActivity.PROPERTY_REG_ID, regid);
            editor.commit();

            Log.d(TAG, "Registrada app en GCM");


        }catch (IOException ex){
            Log.d(TAG, "No nos logramos registrar en GCM");
            Log.d(TAG, ex.getMessage());
        }

        return true;
    }
}
