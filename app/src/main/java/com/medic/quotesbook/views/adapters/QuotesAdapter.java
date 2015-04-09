package com.medic.quotesbook.views.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.QuoteActivity;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.QuoteNetwork;
import com.medic.quotesbook.views.widgets.ImageAutoFitView;
import com.medic.quotesbook.views.widgets.RoundedImageAutoFitView;


import java.util.ArrayList;

/**
 * Created by capi on 25/01/15.
 */
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    public ArrayList<Quote> quotes;

    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bodyView;
        public ImageAutoFitView authorPictureView;
        public TextView authorNameView;

        public ViewHolder(View itemView) {
            super(itemView);

            bodyView = (TextView) itemView.findViewById(R.id.quote_body);
            authorPictureView = (ImageAutoFitView) itemView.findViewById(R.id.author_picture);
            authorNameView = (TextView) itemView.findViewById(R.id.quote_author);

            CardView quotesCardView = (CardView) itemView.findViewById(R.id.quote_card_view);
            quotesCardView.setPreventCornerOverlap(false);

        }

        @Override
        public void onClick(View v) {


        }
    }

    public QuotesAdapter(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complete_quote_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        Quote quote = quotes.get(i);

        holder.bodyView.setText(quote.getBody());
        holder.authorPictureView.setImageBitmap(null);
        holder.authorNameView.setText("- " + quote.getAuthor().getFullName());

        //Log.d("QuotesAdapter", "BindViewHolder");

        holder
        .authorPictureView.setImageUrl("http://quotesbookapp.appspot.com/" + quote.getAuthor().getPictureURL(), imageLoader );

        holder.authorPictureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                ImageAutoFitView imageView = (ImageAutoFitView) v;

                if ( imageView != null && imageView.getDrawable() != null) {
                    Bitmap bitmap = ((RoundedImageAutoFitView.SelectableRoundedCornerDrawable) imageView.getDrawable()).getBitmap();

                    if (bitmap != null) {
                        int diff = 0;

                        if (imageView.getMeasuredHeight() > 0) {


                            if (imageView.getMeasuredWidth() > bitmap.getWidth()) {

                                diff = imageView.getMeasuredWidth() - bitmap.getHeight();
                                bitmap = bitmap.createScaledBitmap(bitmap, imageView.getMeasuredWidth(), bitmap.getHeight() + diff, false);

                                imageView.setImageBitmap(bitmap);
                            }

                            diff = imageView.getMeasuredHeight() - bitmap.getWidth();

                            if (diff > 0)
                                imageView.setImageBitmap(bitmap.createScaledBitmap(bitmap, bitmap.getWidth() + diff, imageView.getMeasuredHeight(), false));

                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (quotes != null)
            return quotes.size();
        else
            return 0;
    }
}
