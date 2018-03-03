package com.medic.quotesbook.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.utils.AdsKeys;
import com.tappx.TAPPXAdBanner;

/**
 * Created by capi on 20/09/15.
 */
public class AdActivity extends AppCompatActivity {

    //public static final double ADMOB_PROBABILITY = 8.5;
    //public static final double ADMOB_PROBABILITY = 0;
    public static final double ADMOB_PROBABILITY = 0.0;

    private AppController app;

    private View adWrapper;
    private AdView adView;

    private TAPPXAdBanner.AdPosition adPosition = TAPPXAdBanner.AdPosition.POSITION_BOTTOM;

    private PublisherAdView tappxAdBanner = null;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        app = (AppController) getApplication();

    }

    public void initAds(){

        adWrapper = findViewById(R.id.ad_wrapper);
        adView = (AdView) findViewById(R.id.ad_view);

        if (app.isAdsActive()){

        if (isTappxTurn())
            setupTappxAd();
        else
            setupAdMob();

        }
    }

    public void setTopAd(){
        adPosition = TAPPXAdBanner.AdPosition.POSITION_TOP;

    }

    private boolean isTappxTurn(){

        double n = Math.random()*10;

        if (n >= ADMOB_PROBABILITY)
            return true;
        else
            return false;
    }

    private void setupAdMob(){

        adView = (AdView) findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("7DC08B2B34AC3B6CD04D5E05DF311803") // Nexus 5
                //.addTestDevice("95523B02E1B93A6E1B4B82DF09FCE7A5") // Logic X3
                .build();

        //adView.setAdSize(AdSize.SMART_BANNER);
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                adWrapper.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                if (errorCode != AdRequest.ERROR_CODE_NETWORK_ERROR)
                    setupTappxAd();
            }
        });


    }

    public void setupTappxAd(){

        tappxAdBanner = TAPPXAdBanner.ConfigureAndShow(this,
                tappxAdBanner, AdsKeys.TAPPX_QUOTEVIEW,
                adPosition, false, new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();

                        adWrapper.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {

                        if (errorCode != AdRequest.ERROR_CODE_NETWORK_ERROR)
                            setupAdMob();
                    }
                });


    }

    public AppController getAppCtrl() {
        return app;
    }

    @Override
    protected void onResume(){
        super.onResume();

        if (tappxAdBanner != null)
            tappxAdBanner.resume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if (tappxAdBanner != null)
            tappxAdBanner.destroy();
    }

    @Override
    protected void onPause(){
        super.onPause();

        if (tappxAdBanner != null)
            tappxAdBanner.pause();
    }

}
