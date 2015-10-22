package com.medic.quotesbook.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.GAK;
import com.medic.quotesbook.utils.QuotesStorage;
import com.medic.quotesbook.views.widgets.RoundedImageView;
import com.squareup.picasso.Picasso;


public class QuoteActivity extends AdActivity {

    final String TAG = this.getClass().getSimpleName();

    static final String SCREEN_NAME_QUOTE_DETAILS = "Quote Details";
    public static final String SCREEN_NAME_DAYQUOTE = "Day Quote";

    public static final String QUOTE_KEY = "quotesbook.quote";
    public static final String DAYQUOTE_KEY = "quotesbook.day_quote"; // Indica si es la cita del día

    TextView quoteBodyView;
    TextView authorNameView;
    RoundedImageView authorPictureView;
    TextView authorDescriptionView;
    FloatingActionButton fab;

    private Quote quote;

    Tracker tracker;

    boolean savedIcon;

    QuotesStorage qStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        initAds();

        tracker = getAppCtrl().getDefaultTracker();

        qStorage = new QuotesStorage(QuotesStorage.QUOTESBOOK_FILE, this);

        quoteBodyView = (TextView) findViewById(R.id.quote_body);
        authorNameView = (TextView) findViewById(R.id.author_name);
        authorDescriptionView = (TextView) findViewById(R.id.author_description);
        authorPictureView = (RoundedImageView) findViewById(R.id.author_picture);

    }

    public void setupContent(){

        quoteBodyView.setText(quote.getBody());

        if (quote.getAuthor() != null){
            authorNameView.setText("- "  + quote.getAuthor().getFullName());
            authorDescriptionView.setText(quote.getAuthor().getShortDescription());
        }

        Picasso.with(this)
                .load(quote.getAuthor().getFullPictureURL())
                .placeholder(R.drawable.author_background_9)
                .error(R.drawable.anonymous_author_1)
                .into(authorPictureView);

    }

    public void setSavedIcon(MenuItem item){

        item.setIcon(R.drawable.ic_star_white_36dp);
    }

    public void setUnsavedIcon(MenuItem item){
        item.setIcon(R.drawable.ic_star_border_white_36dp);
    }

    public void setupFAB(){

        fab = (FloatingActionButton) findViewById(R.id.fab);

        final Context ctx = this;

        fab.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");

                String shareable = quote.getShareable();
                String marketingTail = ", via @" + getResources().getString(R.string.twitter_account);

                if (shareable.length() + marketingTail.length() <= 140)
                    shareable = shareable + marketingTail;

                i.putExtra(Intent.EXTRA_TEXT, shareable);

                startActivity(Intent.createChooser(i, getResources().getString(R.string.title_share_in)));

            }
        });

        // If no Ads

        if (!getAppCtrl().isAdsActive()){

            //// dp to px
            Resources r = this.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    25,
                    r.getDisplayMetrics()
            );
            ////

            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
            lParams.bottomMargin = px;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        quote = this.getIntent().getParcelableExtra(QUOTE_KEY);

        setupContent();
        setupFAB();

        getSupportActionBar().setTitle(quote.getAuthor().getFullName());

        if (this.getIntent().getBooleanExtra(DAYQUOTE_KEY, false)){
            tracker.setScreenName(SCREEN_NAME_DAYQUOTE);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_dayquote));
        }else
            tracker.setScreenName(SCREEN_NAME_QUOTE_DETAILS);

        tracker.send(new HitBuilders.ScreenViewBuilder().build());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_quote, menu);

        if (qStorage.findQuote(quote.getKey()) != -1 ){
            savedIcon = true;
            setSavedIcon(menu.findItem(R.id.action_save_quote));
        }else{
            savedIcon= false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_quote) {

            HitBuilders.EventBuilder event = new HitBuilders.EventBuilder();

            if (savedIcon == false){

                qStorage.addQuoteTop(quote);
                qStorage.commit();

                Toast toast = Toast.makeText(this, R.string.message_quote_saved, Toast.LENGTH_SHORT);
                toast.show();

                setSavedIcon(item);

                event.setCategory(GAK.CATEGORY_QUOTESBOOK);
                event.setAction(GAK.ACTION_QUOTE_SAVED);

                tracker.send(event.build());

            }else{

                qStorage.removeQuote(quote.getKey());
                qStorage.commit();

                Toast toast = Toast.makeText(this, R.string.message_quote_unsaved, Toast.LENGTH_SHORT);
                toast.show();

                setUnsavedIcon(item);

                event.setCategory(GAK.CATEGORY_QUOTESBOOK);
                event.setAction(GAK.ACTION_QUOTE_UNSAVED);

                tracker.send(event.build());
            }

            savedIcon = !savedIcon;

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
