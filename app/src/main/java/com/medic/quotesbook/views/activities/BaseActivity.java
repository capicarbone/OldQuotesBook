package com.medic.quotesbook.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;

import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.receivers.OnBootReceiver;
import com.medic.quotesbook.receivers.QuoteTimeReceiver;
import com.medic.quotesbook.services.PrepareDaysQuoteService;
import com.medic.quotesbook.tasks.RegisterGCMAppTask;
import com.medic.quotesbook.utils.BaseActivityRequestListener;
import com.medic.quotesbook.utils.GAK;
import com.medic.quotesbook.utils.TodayQuoteManager;
import com.medic.quotesbook.views.fragments.DrawerOptionsFragment;
import com.medic.quotesbook.views.fragments.QuotesListFragment;

import android.os.Handler;

public class BaseActivity extends AdActivity implements BaseActivityRequestListener {

    static final String TAG = "BaseActivity";
    public static final String SCREEN_NAME_QUOTESBOOK = "Quotesbook";
    public static final String SCREEN_NAME_SOMEQUOTES = "Some Quotes";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final String PROPERTY_REG_ID = "REGISTRATION_ID";

    private static final String DATA_OPTION_SELECTED = "option_selected";

    GoogleCloudMessaging gcm;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerOptionsView;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mDrawerOptions;

    int optionSelected = 0;

    AppController app;

    Tracker tracker;

    Fragment.SavedState someQuotesFragmentState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        app = (AppController) getApplication();

        if (app.isFirstLaunch()){
            showWelcome();
            finish();
        }else{
            initAds();

            tracker = app.getDefaultTracker();

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_opened, R.string.drawer_closed){

                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);

                }

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };

//        mDrawerOptionsView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerOptions));

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            // Setting the fragment

            FragmentManager fm = getSupportFragmentManager();

            if (savedInstanceState == null){

                Fragment initialFragment = getFragmentForOption(optionSelected);

                fm.beginTransaction()
                        .replace(R.id.frame_content, initialFragment)
                        .add(R.id.frame_drawer_options, new DrawerOptionsFragment())
                        .commit();
            }

            if (checkPlayServices()){
                gcm = GoogleCloudMessaging.getInstance(this);

                String regid;

                regid = getRegistrationId(this);

                if (regid.isEmpty()){
                    Log.d(TAG, "Tenemos que registrarnos");

                    RegisterGCMAppTask register = new RegisterGCMAppTask(gcm);
                    register.execute(this);

                    // We set the alarm.
                    Intent i = new Intent(this, OnBootReceiver.class);
                    sendBroadcast(i);
                }

            }


            tracker.send(new HitBuilders.EventBuilder().build());
        }

        //someTests();

    }

    private Fragment getFragmentForOption(int i){

        Fragment optionView = null;

        switch (i){
            case 0: optionView = QuotesListFragment.newInstance(false);
                tracker.setScreenName(SCREEN_NAME_SOMEQUOTES);
                break;
            case 1: optionView = QuotesListFragment.newInstance(true);
                tracker.setScreenName(SCREEN_NAME_QUOTESBOOK);
                break;
        }

        return optionView;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (mDrawerToggle != null)
            mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(DATA_OPTION_SELECTED, optionSelected);


    }

    @Override
    public void showQuote(Quote quote, boolean dayquote) {

        Intent i = new Intent(this, QuoteActivity.class);
        i.putExtra(QuoteActivity.QUOTE_KEY, quote);
        i.putExtra(QuoteActivity.DAYQUOTE_KEY, dayquote);
        startActivity(i);

        HitBuilders.EventBuilder event = new HitBuilders.EventBuilder();
        event.setCategory(GAK.CATEGORY_QUOTE)
                .setAction(GAK.ACTION_QUOTE_SELECTED)
                .setLabel(quote.getKey());

        tracker.send(event.build());
    }

    @Override
    public void showOption(int optionSelected) {

        Fragment optionView = null;

        FragmentManager fm = getSupportFragmentManager();

        Fragment nextFragment = getFragmentForOption(optionSelected);
        QuotesListFragment actualFragment = (QuotesListFragment) fm.findFragmentById(R.id.frame_content);

        if (optionSelected == 0 && someQuotesFragmentState != null)
            nextFragment.setInitialSavedState(someQuotesFragmentState);

        if ( !actualFragment.isQuotesBook()){ // Es someQuotes
            someQuotesFragmentState = fm.saveFragmentInstanceState(actualFragment);
        }

        fm.beginTransaction()
                .replace(R.id.frame_content, nextFragment)
                .commit();

        mDrawerLayout.closeDrawers();

        tracker.send(new HitBuilders.EventBuilder().build());
    }

    public void showWelcome(){

        final Context activity = this;

        Handler handler = new Handler(new Handler.Callback(){

            @Override
            public boolean handleMessage(Message message) {

                Intent i = new Intent(activity, WelcomeActivity.class);
                startActivity(i);

                return false;
            }
        });

        handler.sendEmptyMessageAtTime(1, 500L);
    }


    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else{
                Log.i(TAG, "This device is not supported.");
            }

            if (resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED){

                HitBuilders.EventBuilder event = new HitBuilders.EventBuilder();
                event.setCategory(GAK.CATEGORY_GPS)
                        .setAction(GAK.ACTION_GPS_UPDATE_REQUIRED);

                tracker.send(event.build());

            }

            return false;
        }

        return true;

    }

    private String getRegistrationId(Context context){

        final SharedPreferences prefs = this.getSharedPreferences(getString(R.string.gcm_preferences), Context.MODE_PRIVATE);

        String registrationId = prefs.getString(PROPERTY_REG_ID, "");

        return registrationId;

    }

    public void showTodayQuote(View v){

        TodayQuoteManager qManager = new TodayQuoteManager(this);

        Quote q = qManager.getTodayQuote();

        showQuote(q, true);

    }

    private void someTests(){

        // For test QuoteTimeRecever

        Intent intent = new Intent(this, OnBootReceiver.class);
        this.sendBroadcast(intent);


        // For test QuoteDayService

//        Intent i = new Intent(this, PrepareDaysQuoteService.class);
//        this.startService(i);

        // For test TodayQuoteManager

//        TodayQuoteManager qm = new TodayQuoteManager(this);
//        qm.getTodayQuote();
    }

}
