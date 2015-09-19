package com.medic.quotesbook.views.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;
import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.utils.BaseActivityRequestListener;
import com.medic.quotesbook.utils.TodayQuoteManager;
import com.medic.quotesbook.views.adapters.DrawerOptionsAdapter;
import com.medic.quotesbook.views.widgets.RoundedImageNetworkView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawerOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawerOptionsFragment extends Fragment {

    static final String TAG = "DrawerOptionsFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView authorName;
    ListView mDrawerOptionsView;
    RoundedImageNetworkView authorPhotoView;

    private Context ctx;
    private BaseActivityRequestListener parentListener;

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

        ctx = this.getActivity();
        parentListener = (BaseActivityRequestListener) ctx;

        View v = inflater.inflate(R.layout.fragment_drawer_options, container, false);

        authorName = (TextView) v.findViewById(R.id.author_name);
        mDrawerOptionsView = (ListView) v.findViewById(R.id.drawer_options_view);
        authorPhotoView = (RoundedImageNetworkView) v.findViewById(R.id.author_picture);

        setupDrawerOptions();

        ShowDayQuoteOnDrawer showQuoteTask = new ShowDayQuoteOnDrawer(authorName, authorPhotoView);
        showQuoteTask.execute();

        //mDrawerOptionsView.setSelected(true);
        //mDrawerOptionsView.setSelection(0);
        mDrawerOptionsView.setItemChecked(0, true);

        return v;
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

    private class ShowDayQuoteOnDrawer extends AsyncTask<Void, Void, Quote>{

        TextView authorName;
        RoundedImageNetworkView authorPhotoView;

        public ShowDayQuoteOnDrawer(TextView authorName, RoundedImageNetworkView authorPhotoView){

            this.authorName = authorName;
            this.authorPhotoView = authorPhotoView;
        }

        @Override
        protected Quote doInBackground(Void... params) {

            Quote quote = null;
            TodayQuoteManager quoteM = new TodayQuoteManager(ctx);

            while(quote == null){

                quote = quoteM.getTodayQuote();

                if (quote == null){
                    try {
                        Thread.sleep(500, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            return quote;

        }

        @Override
        protected void onPostExecute(Quote quote) {

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            if (quote != null){

                if (quote.getAuthor() != null){
                    authorPhotoView.setImageUrl(quote.getAuthor().getFullPictureURL(), imageLoader);
                    authorName.setText("Por " + quote.getAuthor().getFullName());
                }else{
                    authorName.setText("Por Anónimo");
                }

            }

        }
    }

}
