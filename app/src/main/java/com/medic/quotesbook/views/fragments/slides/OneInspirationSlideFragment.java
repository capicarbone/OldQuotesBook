package com.medic.quotesbook.views.fragments.slides;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.medic.quotesbook.R;
import com.squareup.picasso.Picasso;


public class OneInspirationSlideFragment extends Fragment {


    public static OneInspirationSlideFragment newInstance() {
        OneInspirationSlideFragment fragment = new OneInspirationSlideFragment();

        return fragment;
    }

    public OneInspirationSlideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_one_inspiration_slide, container, false);

        ImageView authorView = (ImageView) v.findViewById(R.id.author_picture);

        Picasso.with((Context) getActivity())
                .load(R.drawable.aristoteles)
                .fit()
                .centerCrop()
                .into(authorView);

        return v;
    }


}
