package com.medic.quotesbook.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.medic.quotesbook.R;

/**
 * Created by capi on 16/09/15.
 */
public class DrawerOptionsAdapter extends ArrayAdapter<String> {

    public DrawerOptionsAdapter(Context context, String[] values) {
        super(context, -1, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View optionView = inflater.inflate(R.layout.drawer_option, parent, false);

        TextView optionLabelView = (TextView) optionView.findViewById(R.id.option_label);

        optionLabelView.setText(getItem(position));

        return optionView;


    }
}
