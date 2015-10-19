package com.medic.quotesbook.views.fragments.slides;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.medic.quotesbook.R;
import com.squareup.picasso.Picasso;

public class FindQuotesSlideFragment extends Fragment {


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment FindQuotesSlideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindQuotesSlideFragment newInstance() {
        FindQuotesSlideFragment fragment = new FindQuotesSlideFragment();

        return fragment;
    }

    public FindQuotesSlideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_find_quotes_slide, container, false);

        ImageView authorView = (ImageView) v.findViewById(R.id.author_picture);

        Picasso.with((Context) getActivity())
                .load(R.drawable.shakespeare)
                .fit()
                .centerCrop()
                .into(authorView);

        return v;
    }



}
