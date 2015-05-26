package com.medic.quotesbook.views.activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.medic.quotesbook.R;

import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.tasks.RegisterGCMAppTask;
import com.medic.quotesbook.utils.ChangeActivityRequestListener;
import com.medic.quotesbook.utils.DevUtils;
import com.medic.quotesbook.views.fragments.SomeQuotesFragment;

public class BaseActivity extends ActionBarActivity implements ChangeActivityRequestListener {

    static final String TAG = "BaseActivity";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final String PROPERTY_REG_ID = "REGISTRATION_ID";

    GoogleCloudMessaging gcm;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerOptionsView;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mDrawerOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerOptionsView = (ListView) findViewById(R.id.drawer_options_view);
        mDrawerOptions = getResources().getStringArray(R.array.drawer_options);

        getSupportActionBar().setTitle(R.string.tl_home);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_opened, R.string.drawer_closed){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getResources().getString(R.string.tl_home));
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
            }
        };

        mDrawerOptionsView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerOptions));

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Setting the fragment

       if (savedInstanceState == null) {

           Fragment f = new SomeQuotesFragment();
           FragmentManager fm = getSupportFragmentManager();
           fm.beginTransaction()
                   .add(R.id.frame_content, new SomeQuotesFragment())
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
            }

        };

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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
    public void showQuote(Quote quote) {

        Intent i = new Intent(this, QuoteActivity.class);
        i.putExtra(QuoteActivity.QUOTE_KEY, quote);
        startActivity(i);
    }

    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else{
                Log.i(TAG, "This device is not supported.");
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

}
