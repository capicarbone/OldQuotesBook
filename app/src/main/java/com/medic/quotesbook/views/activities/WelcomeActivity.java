package com.medic.quotesbook.views.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.views.fragments.PresentationFragment;

public class WelcomeActivity extends ActionBarActivity {

    private AppController app;

    ViewPager pager;
    FragmentStatePagerAdapter pagerAdapter;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        app = (AppController) getApplication();

        getSupportActionBar().hide();

        pager = (ViewPager) findViewById(R.id.pager);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        pagerAdapter = new PresentationPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        setupFab();

    }

    public void setupFab(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentItem = pager.getCurrentItem();

                if (currentItem+1 == pager.getAdapter().getCount()){
                    startQuotesBook();
                    finish();
                }
                else
                    pager.setCurrentItem(currentItem + 1);

            }
        });
    }

    public void startQuotesBook(){

        app.setInstallDate();

        Intent i = new Intent(this, BaseActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }


    private class PresentationPagerAdapter extends FragmentStatePagerAdapter{

        public PresentationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = PresentationFragment.newInstance();
            return f;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}
