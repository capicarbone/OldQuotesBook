package com.medic.quotesbook;

import android.app.Application;
import android.content.SharedPreferences;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.appspot.quotesbookapp.quotesclient.Quotesclient;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;
import com.medic.quotesbook.utils.LruBitmapCache;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by capi on 12/03/15.
 */
public class AppController extends Application{

    public static final String TAG = AppController.class.getSimpleName();

    public static final String FLAGS_FILE = "flags";
    public static final String INSTALL_DATE_FLAG = "install_timestamp";

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private Quotesclient quotesClient;

    private static AppController mInstance;

    public static GoogleAnalytics analytics;
    private static Tracker tracker;

    private DateTime installDate;
    private int adsActive = -1; // -1 Sin definido, 1 true, false

    @Override
    public void onCreate() {
        super.onCreate();
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

                installDate = new DateTime();

                SharedPreferences.Editor editor = sp.edit();
                editor.putLong(INSTALL_DATE_FLAG, installDate.getMillis());
                editor.commit();

            }else{

                installDate = new DateTime(installTime);

            }
        }

        if (adsActive == 0){

            DateTime adsAvailableDay = installDate.plusDays(1);

            if( adsAvailableDay.isAfterNow() ){
                adsActive = 1;
            }else{
                adsActive = 0;
            }
        }


        if (adsActive == 1){
            Log.d(TAG, "Ads disponibles");
            return true;
        }
        else{
            Log.d(TAG, "Ads no disponibles");
            return true;

        }

    }


    public RequestQueue getmRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
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

    public ImageLoader getImageLoader(){
        getmRequestQueue();
        if (mImageLoader == null){
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }

        return this.mImageLoader;

    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getmRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }


}
