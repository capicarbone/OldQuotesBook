package com.medic.quotesbook.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by capi on 25/01/15.
 */
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    public ArrayList<Quote> quotes;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView bodyView;

        public ViewHolder(View itemView) {
            super(itemView);

            bodyView = (TextView) itemView.findViewById(R.id.quote_body);
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
        holder.bodyView.setText(quotes.get(i).getBody());
    }

    @Override
    public int getItemCount() {
        if (quotes != null)
            return quotes.size();
        else
            return 0;
    }
}
