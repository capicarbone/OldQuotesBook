package com.medic.quotesbook;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import io.fabric.sdk.android.Fabric;
import org.joda.time.DateTime;

/**
 * Created by capi on 12/03/15.
 */
public class AppController extends MultiDexApplication{

    public static final String TAG = AppController.class.getSimpleName();

    public static final String FLAGS_FILE = "flags";
    public static final String INSTALL_DATE_FLAG = "install_timestamp";

    private Quotesclient quotesClient;

    private static AppController mInstance;

    public static GoogleAnalytics analytics;
    private static Tracker tracker;

    private DateTime installDate;
    private int adsActive = -1; // -1 Sin definidr, 1 true, false

    @Override
    public void onCreate() {
        super.onCreate();
        // Crashlytics Initialize
        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
        // ---
        mInstance = this;

        //isAdsActive();

    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }

    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            tracker = analytics.newTracker(R.xml.global_tracker);
        }
        return tracker;
    }

    synchronized public boolean isAdsActive() {

        if (installDate == null) {

            SharedPreferences sp = this.getSharedPreferences(FLAGS_FILE, this.MODE_PRIVATE);
            Long installTime = sp.getLong(INSTALL_DATE_FLAG, 0);

            if (installTime == 0){

                setInstallDate();

            }else{

                installDate = new DateTime(installTime);

            }
        }

        if (adsActive == -1){

            DateTime adsAvailableDay = installDate.plusDays(1);

            if( adsAvailableDay.isBeforeNow() ){
                adsActive = 1;
            }else{
                adsActive = 0;
            }
        }

/*
        if (adsActive == 1){
            return true; // Ads availables
        }
        else{
            return false; // Ads no availables

        }*/

        return false;

    }

    public void setInstallDate(){
        installDate = new DateTime();
        SharedPreferences sp = this.getSharedPreferences(FLAGS_FILE, this.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(INSTALL_DATE_FLAG, installDate.getMillis());
        editor.commit();
    }

    public boolean isFirstLaunch(){


        if (installDate == null) {

            SharedPreferences sp = this.getSharedPreferences(FLAGS_FILE, this.MODE_PRIVATE);
            Long installTime = sp.getLong(INSTALL_DATE_FLAG, 0);

            if (installTime == 0){
                return true;

            }else{

                return false;

            }
        }

        return false;
    }

    public Quotesclient getQuotesClient(){

        if (quotesClient == null){
            Quotesclient.Builder builder = new Quotesclient.Builder(AndroidHttp.newCompatibleTransport(),
                    new GsonFactory(),
                    null );

            return builder.build();
        }

        return quotesClient;
    }



}
