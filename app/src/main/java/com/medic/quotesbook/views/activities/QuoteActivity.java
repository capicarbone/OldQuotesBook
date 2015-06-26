package com.medic.quotesbook.views.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.views.widgets.RoundedImageNetworkView;


public class QuoteActivity extends ActionBarActivity {

    static final String TAG = "QuoteActivity";

    public static final String QUOTE_KEY = "quotesbook.quote";

    TextView quoteBodyView;
    TextView authorNameView;
    RoundedImageNetworkView authorPictureView;
    TextView authorDescriptionView;

    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    private Quote quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        quoteBodyView = (TextView) findViewById(R.id.quote_body);
        authorNameView = (TextView) findViewById(R.id.author_name);
        authorDescriptionView = (TextView) findViewById(R.id.author_description);
        authorPictureView = (RoundedImageNetworkView) findViewById(R.id.author_picture);

        quote = this.getIntent().getParcelableExtra(QUOTE_KEY);

        quoteBodyView.setText(quote.getBody());

        if (quote.getAuthor() != null){
            authorNameView.setText("- "  + quote.getAuthor().getFullName());
            authorDescriptionView.setText(quote.getAuthor().getShortDescription());
        }

        authorPictureView.setImageUrl("http://quotesbookapp.appspot.com/" + quote.getAuthor().getPictureUrl(), imageLoader);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(quote.getAuthor().getFullName());
    }

    /*
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

    */
}
