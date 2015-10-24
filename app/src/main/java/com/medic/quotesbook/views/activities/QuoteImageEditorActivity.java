package com.medic.quotesbook.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.squareup.picasso.Picasso;

public class QuoteImageEditorActivity extends AppCompatActivity {

    public static final String QUOTE_KEY = "quotesbook.quote";

    ImageView quoteBackgroundView;
    ImageView authorPictureView;
    TextView quoteBodyView;

    TextView authorFirstNameView;
    TextView authorLastNameView;

    private Quote quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_image_editor);

        quote = getIntent().getParcelableExtra(QUOTE_KEY);

        quoteBackgroundView = (ImageView) findViewById(R.id.quote_background);
        authorPictureView = (ImageView) findViewById(R.id.author_picture);
        quoteBodyView = (TextView) findViewById(R.id.quote_body);

        authorFirstNameView = (TextView) findViewById(R.id.author_first_name);
        authorLastNameView = (TextView) findViewById(R.id.author_last_name);

        setContent();

    }

    private void setContent(){

        // TODO: Si no hay red avisar de que no se puede obtener la imagen del autor

        quoteBodyView.setText(quote.getBody());

        Picasso.with(this)
                .load(quote.getAuthor().getFullPictureURL())
                .placeholder(R.drawable.author_background_9)
                .error(R.drawable.anonymous_author_1)
                .into(authorPictureView);

        if (quote.getAuthor().getLastName().equals("")){
            authorLastNameView.setText(quote.getAuthor().getFirstName());
        }else{
            authorLastNameView.setText(quote.getAuthor().getLastName());
            authorFirstNameView.setText(quote.getAuthor().getFirstName());
        }

    }
}
