package com.medic.quotesbook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.medic.quotesbook.models.Quote;


public class QuoteActivity extends ActionBarActivity {

    public static final String QUOTE_KEY = "quotesbook.quote";

    TextView quoteBodyView;

    private Quote quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        quoteBodyView = (TextView) findViewById(R.id.quote_body);

        quote = this.getIntent().getParcelableExtra(QUOTE_KEY);

        quoteBodyView.setText(quote.getBody());


    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(quote.getAuthor().getFullName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
