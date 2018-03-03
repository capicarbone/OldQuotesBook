package com.medic.quotesbook.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.views.fragments.PresentationFragment;
import com.medic.quotesbook.views.fragments.slides.FindQuotesSlideFragment;
import com.medic.quotesbook.views.fragments.slides.OneInspirationSlideFragment;
import com.medic.quotesbook.views.fragments.slides.SaveAndShareSlideFragment;
import com.medic.quotesbook.views.fragments.slides.SupportUsSlideFragment;

public class WelcomeActivity extends AppCompatActivity {

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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            final Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(this.getResources().getColor(R.color.slide_background_1));
            window.setNavigationBarColor(this.getResources().getColor(R.color.slide_background_1));

            final Context ctx = this;


            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        switch (position){
                            case 0:
                                window.setStatusBarColor(ctx.getResources().getColor(R.color.slide_background_1));
                                window.setNavigationBarColor(ctx.getResources().getColor(R.color.slide_background_1));
                                break;
                            case 1:
                                window.setStatusBarColor(ctx.getResources().getColor(R.color.slide_background_2));
                                window.setNavigationBarColor(ctx.getResources().getColor(R.color.slide_background_2));
                                break;
                            case 2:
                                window.setStatusBarColor(ctx.getResources().getColor(R.color.slide_background_3));
                                window.setNavigationBarColor(ctx.getResources().getColor(R.color.slide_background_3));
                                break;
                            case 3:
                                window.setStatusBarColor(ctx.getResources().getColor(R.color.slide_background_4));
                                window.setNavigationBarColor(ctx.getResources().getColor(R.color.slide_background_4));
                                break;
                        }
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }


    }

    public void setupFab(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentItem = pager.getCurrentItem();

                if (currentItem+1 == pager.getAdapter().getCount()){ // Si es la última página
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

            switch (position){
                case 0: return FindQuotesSlideFragment.newInstance();
                case 1: return SaveAndShareSlideFragment.newInstance();
                case 2: return OneInspirationSlideFragment.newInstance();
                case 3: return SupportUsSlideFragment.newInstance();
                default:
                    return PresentationFragment.newInstance();
            }

        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
