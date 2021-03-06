package com.medic.quotesbook.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.GAK;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QuoteImageEditorActivity extends AdActivity {

    private final String TAG = this.getClass().getSimpleName();

    private static final String SCREEN_NAME_IMAGE_QUOTE_EDITOR = "image quote editor";

    public static final String QUOTE_KEY = "quotesbook.quote";

    public static final int LIMIT_SHORT_QUOTE = 80;
    public static final int LIMIT_SMALL_QUOTE = 130;
    public static final int LIMIT_MEDIUM_QUOTE = 180;

    public static final int N_QUOTE_BACKGROUND = 2;

    ImageView quoteBackgroundView;
    ImageView authorPictureView;
    TextView quoteBodyTV;
    TextView qbLogoQuotesTV;
    TextView qbLogoBookTV;

    TextView authorFirstNameTV;
    TextView authorLastNameTV;
    View quoteImageRoot;
    FloatingActionButton fab;
    View progessBar;

    Tracker tracker;

    String imageFileUrl;

    private Quote quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_image_editor);

        tracker  = getAppCtrl().getDefaultTracker();

        quote = getIntent().getParcelableExtra(QUOTE_KEY);

        quoteBackgroundView = (ImageView) findViewById(R.id.quote_background);
        authorPictureView = (ImageView) findViewById(R.id.author_picture);
        quoteBodyTV = (TextView) findViewById(R.id.quote_body);

        authorFirstNameTV = (TextView) findViewById(R.id.author_first_name);
        authorLastNameTV = (TextView) findViewById(R.id.author_last_name);

        qbLogoQuotesTV = (TextView) findViewById(R.id.qb_lobo_quotes);
        qbLogoBookTV = (TextView) findViewById(R.id.qb_lobo_book);

        quoteImageRoot = findViewById(R.id.image_quote_root);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        progessBar = findViewById(R.id.progressBar);

        setTopAd();
        initAds();

        setupFab();
        setContent();

    }

    private void setContent(){

        // TODO: Si no hay red avisar de que no se puede obtener la imagen del autor

        String shareableQuote = quote.getWithQuotes();

        /*
        if (shareableQuote.length() < LIMIT_SHORT_QUOTE){
            quoteBodyTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        }

        if (shareableQuote.length() > LIMIT_SMALL_QUOTE){
            quoteBodyTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        }

        if (shareableQuote.length() > LIMIT_MEDIUM_QUOTE){
            quoteBodyTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        }
        */

        quoteBodyTV.setText(shareableQuote);

        Picasso.with(this)
                .load(quote.getAuthor().getFullPictureURL())
                .placeholder(R.drawable.author_background_9)
                .error(R.drawable.anonymous_author_1)
                .into(authorPictureView, new Callback(){

                    @Override
                    public void onSuccess() {
                        progessBar.setVisibility(View.GONE);
                        fab.show(true);
                    }

                    @Override
                    public void onError() {

                    }
                });

        if (quote.getAuthor().getLastName().equals("")){
            authorLastNameTV.setText(quote.getAuthor().getFirstName());
        }else{
            authorLastNameTV.setText(quote.getAuthor().getLastName());
            authorFirstNameTV.setText(quote.getAuthor().getFirstName());
        }

        int nBackground = (int) (Math.random() * N_QUOTE_BACKGROUND) % N_QUOTE_BACKGROUND ;
        //nBackground = 5;

        quoteBackgroundView.setImageResource(getBackgroundQuoteResource(nBackground));
        quoteBodyTV.setTextColor(getResources().getColor(getImageTextColorResource(nBackground)));
        authorFirstNameTV.setTextColor(getResources().getColor(getImageTextColorResource(nBackground)));
        authorLastNameTV.setTextColor(getResources().getColor(getImageTextColorResource(nBackground)));
        ((TextView) findViewById(R.id.author_separator)).setTextColor(getResources().getColor(getImageTextColorResource(nBackground)));

        Typeface font = Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf");
        quoteBodyTV.setTypeface(font);

        Typeface fontBold = Typeface.createFromAsset(getAssets(), "RobotoSlab-Bold.ttf");
        qbLogoBookTV.setTypeface(fontBold);
        qbLogoQuotesTV.setTypeface(font);

        ( (TextView) findViewById(R.id.qb_lobo_quotes_light)).setTypeface(font);
        ( (TextView) findViewById(R.id.qb_lobo_book_light)).setTypeface(fontBold);


    }

    private void setupFab(){

        fab.hide(false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fab.setShowProgressBackground(true);
                fab.setIndeterminate(true);

                Bitmap image = getBitmapFromView(quoteImageRoot);

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("image/jpeg");


                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                imageFileUrl = Environment.getExternalStorageDirectory() + File.separator + quote.getKey() + ".jpg";


                File f = new File(imageFileUrl);
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + imageFileUrl));

                startActivity(Intent.createChooser(sharingIntent, "Compartir via"));

                HitBuilders.EventBuilder event = new HitBuilders.EventBuilder();

                event.setCategory(GAK.CATEGORY_SHARE);
                event.setAction(GAK.ACTION_QUOTE_IMAGE_SHARED);
                event.setLabel(quote.getKey());

                tracker.send(event.build());

            }
        });
    }



    public static Bitmap getBitmapFromView(View view) {
        // CODE FROM: http://stackoverflow.com/questions/5536066/convert-view-to-bitmap-on-android

        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.d(TAG, "Se activa el loader");
    }

    @Override
    protected void onResume(){
        super.onResume();

        tracker.setScreenName(SCREEN_NAME_IMAGE_QUOTE_EDITOR);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        fab.hideProgress();

        /*if (imageFileUrl != null){

            File f = new File(imageFileUrl);
            f.delete();

            imageFileUrl = null;
        }*/
    }

    private int getBackgroundQuoteResource(int i){

        switch (i){
            case 0: return R.drawable.bg_quote_share_1;
            case 1: return R.drawable.bg_quote_share_2;
            case 2: return R.drawable.bg_quote_share_3;
            case 3: return R.drawable.bg_quote_share_4;
            case 4: return R.drawable.bg_quote_share_5;
            default:
                return R.drawable.bg_quote_share_2;

        }
    }

    private int getImageTextColorResource(int i){
        switch (i){
            case 1:
                findViewById(R.id.bottom_dark).setVisibility(View.GONE);
                return R.color.text_color_2;
            default:
                findViewById(R.id.bottom_light).setVisibility(View.GONE);
                return R.color.text_color_1;
        }
    }
}
