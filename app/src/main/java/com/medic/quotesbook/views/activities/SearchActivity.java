package com.medic.quotesbook.views.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.medic.quotesbook.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_search);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager  = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));
        searchView.setIconified(false);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("SearchActivity", "SearchView cerrado");

                // TODO: Implement back.

                return true;
            }
        });

        return true;
    }

}
