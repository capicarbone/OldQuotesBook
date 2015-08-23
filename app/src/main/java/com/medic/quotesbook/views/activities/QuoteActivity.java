package com.medic.quotesbook.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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

        setupAd();
        setupContent();
        setupFAB();

    }

    public void setupAd(){

        AdView adView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("7DC08B2B34AC3B6CD04D5E05DF311803") // Nexus 5
                .addTestDevice("95523B02E1B93A6E1B4B82DF09FCE7A5") // Logic X3
                .build();

        adView.loadAd(adRequest);

    }

    public void setupContent(){

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

        authorPictureView.setImageUrl(quote.getAuthor().getFullPictureURL(), imageLoader);
    }

    public void FABClick(View view){
        Log.d(TAG, "Presionado");
    }

    public void setupFAB(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final Context ctx = this;

        fab.setOnClickListener(new OnClickListener(){

            private boolean saved = false;

            @Override
            public void onClick(View view) {

                Log.d(TAG, "Presionado");

                FloatingActionButton btn = (FloatingActionButton) view;

                if (saved == false){

                    Toast toast = Toast.makeText(ctx, R.string.message_quote_saved, Toast.LENGTH_SHORT);
                    toast.show();

                    btn.setImageResource(R.drawable.ic_star_white_24dp);

                }else{

                    Toast toast = Toast.makeText(ctx, R.string.message_quote_unsaved, Toast.LENGTH_SHORT);
                    toast.show();

                    btn.setImageResource(R.drawable.ic_star_border_white_24dp);
                }

                saved = !saved;

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(quote.getAuthor().getFullName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_quote, menu);

        MenuItem item = menu.findItem(R.id.action_share);

        ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, quote.getShareable());
        intent.setType("text/plain");
        Intent.createChooser(intent, "QuotesBook");

        actionProvider.setShareIntent(intent);

        return true;
    }



    /*
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
