package com.medic.quotesbook.views.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.BaseActivityRequestListener;
import com.medic.quotesbook.utils.TodayQuoteManager;
import com.medic.quotesbook.views.adapters.DrawerOptionsAdapter;
import com.medic.quotesbook.views.widgets.RoundedImageView;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawerOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawerOptionsFragment extends Fragment {

    static final String TAG = "DrawerOptionsFragment";

    TextView authorName;
    ListView mDrawerOptionsView;
    RoundedImageView authorPhotoView;
    TextView drawerTitleView;

    private Context ctx;
    private BaseActivityRequestListener parentListener;

    BroadcastReceiver dayQuoteReceiver;


    public static DrawerOptionsFragment newInstance(String param1, String param2) {
        DrawerOptionsFragment fragment = new DrawerOptionsFragment();
        return fragment;
    }

    public DrawerOptionsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ctx = this.getActivity();
        parentListener = (BaseActivityRequestListener) ctx;

        dayQuoteReceiver = new DayQuoteAvailableReceiver();

        View v = inflater.inflate(R.layout.fragment_drawer_options, container, false);

        authorName = (TextView) v.findViewById(R.id.author_name);
        mDrawerOptionsView = (ListView) v.findViewById(R.id.drawer_options_view);
        authorPhotoView = (RoundedImageView) v.findViewById(R.id.author_picture);
        drawerTitleView = (TextView) v.findViewById(R.id.days_quote_title);

        setupDrawerOptions();

        //mDrawerOptionsView.setSelected(true);
        //mDrawerOptionsView.setSelection(0);
        mDrawerOptionsView.setItemChecked(0, true);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        if ( !inflateDayQuote() );
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(dayQuoteReceiver, new IntentFilter(DayQuoteAvailableReceiver.INTENT_EVENT));
    }

    @Override
    public void onPause(){
        super.onPause();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(dayQuoteReceiver);
    }

    public void setupDrawerOptions(){

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);

        DrawerOptionsAdapter adapter = new DrawerOptionsAdapter(getActivity(), mDrawerOptions);

        mDrawerOptionsView.setAdapter(adapter);

        mDrawerOptionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            TextView lastOptionSelected = null;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                parentListener.showOption(position);

            }
        });
    }

    private boolean inflateDayQuote(){
        Quote quote = null;
        TodayQuoteManager quoteM = new TodayQuoteManager(ctx);

        quote = quoteM.getTodayQuote();

        String from = getActivity().getResources().getString(R.string.by);
        String anonymous = getActivity().getResources().getString(R.string.anonymous_author);

        if (quote != null){

            drawerTitleView.setText(ctx.getResources().getText(R.string.title_dayquote));

            if (quote.getAuthor() != null){
                //authorPhotoView.setImageUrl(quote.getAuthor().getFullPictureURL(), imageLoader);
                Picasso.with(ctx)
                        .load(quote.getAuthor().getFullPictureURL())
                        .error(R.drawable.author_background_7)
                        .placeholder(R.drawable.author_background_7)
                        .into(authorPhotoView);
                authorName.setText(from + " " + quote.getAuthor().getFullName());


            }else{
                authorName.setText(from + " " + anonymous);
            }

            return true;

        }

        return false;
    }

    public class DayQuoteAvailableReceiver extends BroadcastReceiver{

        public static final String INTENT_EVENT = "DayQuoteAvailable";

        @Override
        public void onReceive(Context context, Intent intent) {

            inflateDayQuote();
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(dayQuoteReceiver);

        }
    }

}
