package com.medic.quotesbook.views.fragments.slides;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medic.quotesbook.R;


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

        return inflater.inflate(R.layout.fragment_one_inspiration_slide, container, false);
    }


}
