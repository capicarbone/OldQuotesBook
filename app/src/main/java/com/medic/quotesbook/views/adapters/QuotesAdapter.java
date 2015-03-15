package com.medic.quotesbook.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.QuoteNetwork;


import java.util.ArrayList;

/**
 * Created by capi on 25/01/15.
 */
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    public ArrayList<Quote> quotes;

    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView bodyView;
        public NetworkImageView authorPictureView;
        public int height = -1;

        public ViewHolder(View itemView) {
            super(itemView);

            //authorPictureView = (NetworkImageView) itemView.findViewById(R.id.author_picture);
            bodyView = (TextView) itemView.findViewById(R.id.quote_body);

            if (height == -1){
                height = itemView.getHeight();
            }else{
                itemView.setMinimumHeight(height);
            }

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

        //holder
        //.authorPictureView.setImageUrl("http://quotesbookapp.appspot.com/" + quote.getAuthor().getPictureURL(), imageLoader );
    }

    @Override
    public int getItemCount() {
        if (quotes != null)
            return quotes.size();
        else
            return 0;
    }
}
