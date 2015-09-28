package com.medic.quotesbook.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.BaseActivityRequestListener;
import com.medic.quotesbook.views.widgets.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


import java.util.ArrayList;

/**
 * Created by capi on 25/01/15.
 */
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    public ArrayList<Quote> quotes;
    private BaseActivityRequestListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bodyView;
        public RoundedImageView authorPictureView;
        public TextView authorNameView;

        public Quote quote;

        private BaseActivityRequestListener listener;
        private Context ctx;

        public ViewHolder(View itemView, Quote quote, BaseActivityRequestListener listener) {
            super(itemView);

            this.listener = listener;
            this.ctx = (Context) listener;
            this.quote = quote;

            bodyView = (TextView) itemView.findViewById(R.id.quote_body);
            authorPictureView = (RoundedImageView) itemView.findViewById(R.id.author_picture);
            authorNameView = (TextView) itemView.findViewById(R.id.quote_author);

            CardView quotesCardView = (CardView) itemView.findViewById(R.id.quote_card_view);
            quotesCardView.setPreventCornerOverlap(false);

            itemView.setOnClickListener(this);

            // TODO: Next to Author Class

            int[] authorsColors = new int[8];
            authorsColors[0] = R.color.author_background_1;
            authorsColors[1] = R.color.author_background_2;
            authorsColors[2] = R.color.author_background_3;
            authorsColors[3] = R.color.author_background_4;
            authorsColors[4] = R.color.author_background_5;
            authorsColors[5] = R.color.author_background_6;
            authorsColors[6] = R.color.author_background_7;
            authorsColors[7] = R.color.author_background_8;

            int colorPos = (int) ((Math.random()*10) % 8);
            authorPictureView.setBackgroundColor(ctx.getResources().getColor(authorsColors[colorPos]));

        }

        @Override
        public void onClick(View v) {

            Log.d("QuotesAdapter", "Click sobre " + authorNameView.getText() );

            listener.showQuote(this.quote, false);


        }
    }

    public QuotesAdapter(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    public QuotesAdapter(ArrayList<Quote> quotes, BaseActivityRequestListener listener) {
        this.quotes = quotes;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complete_quote_layout, parent, false);

        ViewHolder vh = new ViewHolder(v, quotes.get(i), listener);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {

        final Quote quote = quotes.get(i);

        holder.bodyView.setText(quote.getBody());
        holder.authorPictureView.setImageBitmap(null);

        holder.authorNameView.setText("- " + quote.getAuthor().getFullName());
        holder.quote = quote;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            holder.authorPictureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                    RoundedImageView imageView = (RoundedImageView) v;

                    Picasso.with((Context) listener)
                            .load(quote.getAuthor().getFullPictureURL())
                            .resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight())
                            .centerCrop()
                            .into(imageView);

                }
            });
        }else{

            // TODO: Quizás debería quitar esto.

            ViewTreeObserver vto = holder.authorPictureView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    RoundedImageView imageView = holder.authorPictureView;

                    Picasso.with((Context) listener)
                            .load(quote.getAuthor().getFullPictureURL())
                            .resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight())
                            .centerCrop()
                            .into(imageView);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (quotes != null)
            return quotes.size();
        else
            return 0;
    }
}
