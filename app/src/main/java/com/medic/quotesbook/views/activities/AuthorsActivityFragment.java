package com.medic.quotesbook.views.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medic.quotesbook.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AuthorsActivityFragment extends Fragment {

    public AuthorsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authors, container, false);
    }
}
