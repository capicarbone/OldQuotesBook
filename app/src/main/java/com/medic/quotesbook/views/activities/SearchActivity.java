package com.medic.quotesbook.views.activities;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.medic.quotesbook.R;
import com.medic.quotesbook.tasks.GetQuotesTask;
import com.medic.quotesbook.tasks.QuotesFromServerTask;
import com.medic.quotesbook.tasks.SearchQuoteTask;
import com.medic.quotesbook.views.fragments.QuotesListFragment;

public class SearchActivity extends AdActivity implements QuotesListFragment.ContextActivity{

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_search);

        initAds();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        final Activity act = this;

        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager  = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setIconified(false);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                act.finish();
                return true;
            }
        });

        return true;
    }


    @Override
    protected void onNewIntent(Intent intent){

        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            query = intent.getStringExtra(SearchManager.QUERY);
        }

        startSearch();

    }

    private void startSearch(){

        QuotesListFragment listFragment = QuotesListFragment.newInstance(true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.list_fragment, listFragment);
        transaction.commit();
    }


    @Override
    public GetQuotesTask getQuotesProviderTask() {

        QuotesFromServerTask task = new SearchQuoteTask();
        task.setQuery(query);
        task.setPageSize(QuotesFromServerTask.DEFAULT_PAGE_SIZE);

        return task;
    }
}
