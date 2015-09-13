package com.medic.quotesbook;

import android.app.Application;
import android.media.Image;
import android.text.TextUtils;

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

/**
 * Created by capi on 12/03/15.
 */
public class AppController extends Application{

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private Quotesclient quotesClient;

    private static AppController mInstance;

    public static GoogleAnalytics analytics;
    private static Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //analytics = GoogleAnalytics.getInstance(this);
        //analytics.setLocalDispatchPeriod(1800);

        //tracker = analytics.newTracker("UA-66374922-2");
        //tracker.enableExceptionReporting(true);
        //tracker.enableAdvertisingIdCollection(true);
        //tracker.enableAutoActivityTracking(true);
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
