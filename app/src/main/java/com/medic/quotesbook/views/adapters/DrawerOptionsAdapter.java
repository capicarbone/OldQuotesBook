package com.medic.quotesbook.views.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medic.quotesbook.R;

/**
 * Created by capi on 16/09/15.
 */
public class DrawerOptionsAdapter extends ArrayAdapter<String> {

    private TextView lastOptionSelected;

    public DrawerOptionsAdapter(Context context, String[] values) {
        super(context, -1, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View optionView = inflater.inflate(R.layout.drawer_option, parent, false);

        ImageView optionIconView = (ImageView) optionView.findViewById(R.id.option_icon);
        TextView optionLabelView = (TextView) optionView.findViewById(R.id.option_label);

        optionLabelView.setText(getItem(position));

        Drawable icon = null;

        switch (position){
            case 0:
                icon = getContext().getResources().getDrawable(R.drawable.ic_quote_selector);
                break;
            case 1:
                // TODO: Colocar if para diferentes versiones.
                icon = getContext().getResources().getDrawable(R.drawable.ic_book_selector);
                break;
        }

        optionIconView.setBackgroundDrawable(icon);

        return optionView;

    }

    public TextView getLastOptionSelected() {
        return lastOptionSelected;
    }

    public void setLastOptionSelected(TextView lastOptionSelected) {
        this.lastOptionSelected = lastOptionSelected;
    }
}
