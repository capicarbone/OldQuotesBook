package com.medic.quotesbook.views.fragments.slides;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medic.quotesbook.R;


public class SupportUsSlideFragment extends Fragment {

    public static SupportUsSlideFragment newInstance() {
        SupportUsSlideFragment fragment = new SupportUsSlideFragment();

        return fragment;
    }

    public SupportUsSlideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support_us_slide, container, false);
    }


}
