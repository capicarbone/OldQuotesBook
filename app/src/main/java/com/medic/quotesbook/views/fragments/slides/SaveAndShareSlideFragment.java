package com.medic.quotesbook.views.fragments.slides;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medic.quotesbook.R;


public class SaveAndShareSlideFragment extends Fragment {


    public static SaveAndShareSlideFragment newInstance() {
        SaveAndShareSlideFragment fragment = new SaveAndShareSlideFragment();
        return fragment;
    }

    public SaveAndShareSlideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_save_and_share_slide, container, false);
    }


}
