package com.medic.quotesbook.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.BaseActivityRequestListener;
import com.medic.quotesbook.utils.GAK;
import com.medic.quotesbook.views.activities.QuoteActivity;
import com.medic.quotesbook.views.widgets.RoundedImageView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

/**
 * Created by capi on 25/01/15.
 */
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    private static final String TAG = "QuotesAdapter";

    private static final int VIEW_TYPE_FOOTER = 10;
    private static final int VIEW_TYPE_QUOTE = 0;
    private Tracker tracker;

    public ArrayList<Quote> quotes;

    private boolean withLoader = false;

    private Context ctx;

    public QuotesAdapter(ArrayList<Quote> quotes, Context ctx, Tracker tracker) {
        this.quotes = quotes;
        this.ctx = ctx;
        this.tracker = tracker;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bodyView;
        public RoundedImageView authorPictureView;
        public TextView authorNameView;

        public Quote quote;

        public Drawable backgroundColor;

        private BaseActivityRequestListener listener;
        private Context ctx;
        private Tracker tracker;

        public ViewHolder(View itemView){
            super(itemView);
        }

        public ViewHolder(View itemView, Quote quote, Context ctx, Tracker tracker) {
            super(itemView);

            this.listener = listener;
            this.ctx = ctx;
            this.quote = quote;
            this.tracker = tracker;

            bodyView = (TextView) itemView.findViewById(R.id.quote_body);
            authorPictureView = (RoundedImageView) itemView.findViewById(R.id.author_picture);
            authorNameView = (TextView) itemView.findViewById(R.id.quote_author);

            CardView quotesCardView = (CardView) itemView.findViewById(R.id.quote_card_view);
            quotesCardView.setPreventCornerOverlap(false);

            itemView.setOnClickListener(this);

            backgroundColor = ctx.getResources().getDrawable(getAuthorBackgroundColor());

        }

        public int getAuthorBackgroundColor(){

            int N_COLORS = 9;

            int[] authorsColors = new int[N_COLORS];
            authorsColors[0] = R.drawable.author_background_1;
            authorsColors[1] = R.drawable.author_background_2;
            authorsColors[2] = R.drawable.author_background_3;
            authorsColors[3] = R.drawable.author_background_4;
            authorsColors[4] = R.drawable.author_background_5;
            authorsColors[5] = R.drawable.author_background_6;
            authorsColors[6] = R.drawable.author_background_7;
            authorsColors[7] = R.drawable.author_background_8;
            authorsColors[8] = R.drawable.author_background_9;

            int colorPos = (int) ((Math.random()*10) % N_COLORS);

            return authorsColors[colorPos];
        }

        @Override
        public void onClick(View v) {

            //Log.d("QuotesAdapter", "Click sobre " + authorNameView.getText() );

            Intent i = new Intent(ctx, QuoteActivity.class);
            i.putExtra(QuoteActivity.QUOTE_KEY, quote);
            ctx.startActivity(i);

            HitBuilders.EventBuilder event = new HitBuilders.EventBuilder();
            event.setCategory(GAK.CATEGORY_QUOTE)
                    .setAction(GAK.ACTION_QUOTE_SELECTED)
                    .setLabel(quote.getKey());

            tracker.send(event.build()); // TODO: Maybe unnecessary

        }
    }

    public QuotesAdapter(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    public void withLoader(boolean withLoader) {
        this.withLoader = withLoader;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = null;
        ViewHolder vh = null;

        if (i == VIEW_TYPE_QUOTE){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.complete_quote_layout, parent, false);

            vh = new ViewHolder(v, quotes.get(i), ctx, tracker);
        }else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.somequotes_footer, parent, false);

            vh = new ViewHolder(v);
        }


        return vh;
    }

    @Override
    public int getItemViewType(int position){

        if (position < quotes.size())
            return VIEW_TYPE_QUOTE;
        else
            return VIEW_TYPE_FOOTER;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {

        if (i < quotes.size()){

            final Quote quote = quotes.get(i);

            holder.bodyView.setText(quote.getBody());
            holder.authorPictureView.setImageBitmap(null);

            holder.authorNameView.setText("- " + quote.getAuthor().getFullName());
            holder.quote = quote;


            holder.authorPictureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                    final RoundedImageView imageView = (RoundedImageView) v;


                    Picasso.with(ctx)
                            .load(quote.getAuthor().getFullPictureURL())
                            .placeholder(holder.backgroundColor)
                            .fit()
                            .centerCrop()
                            .into(imageView);

                    // TODO: Usar .error() para colocar la silueta previamente resize. Se podría
                    // guardar el resize en el holder para evitar calcularlo cada llamada

                }
            });
        }




    }

    @Override
    public int getItemCount() {
        if (quotes != null)
            if (withLoader)
                return quotes.size() + 1;
            else
                return quotes.size();
        else
            return 0;
    }
}
