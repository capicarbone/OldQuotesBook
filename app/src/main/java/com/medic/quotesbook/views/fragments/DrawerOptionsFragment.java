package com.medic.quotesbook.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.DaysQuoteManager;
import com.medic.quotesbook.views.widgets.RoundedImageNetworkView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawerOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawerOptionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView authorName;
    ListView mDrawerOptionsView;
    RoundedImageNetworkView authorPhotoView;

    private Context ctx;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrawerOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrawerOptionsFragment newInstance(String param1, String param2) {
        DrawerOptionsFragment fragment = new DrawerOptionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DrawerOptionsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        ctx = this.getActivity();
        View v = inflater.inflate(R.layout.fragment_drawer_options, container, false);

        authorName = (TextView) v.findViewById(R.id.author_name);
        mDrawerOptionsView = (ListView) v.findViewById(R.id.drawer_options_view);
        authorPhotoView = (RoundedImageNetworkView) v.findViewById(R.id.author_picture);

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);

        mDrawerOptionsView.setAdapter(new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, mDrawerOptions));

        DaysQuoteManager quoteM = new DaysQuoteManager(ctx);

        Quote quote = quoteM.getTodayQuote();

        if (quote != null && quote.getAuthor() != null){
            authorPhotoView.setImageUrl(quote.getAuthor().getFullPictureURL(), imageLoader);
            authorName.setText("Por " + quote.getAuthor().getFullName());
        }

        return v;
    }


}
