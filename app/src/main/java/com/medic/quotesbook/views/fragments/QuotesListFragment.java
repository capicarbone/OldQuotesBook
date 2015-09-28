package com.medic.quotesbook.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.medic.quotesbook.AppController;
import com.medic.quotesbook.R;

import com.medic.quotesbook.tasks.GetQuotesTask;
import com.medic.quotesbook.tasks.GetQuotesbookTask;
import com.medic.quotesbook.tasks.GetSomeQuotesTask;
import com.medic.quotesbook.utils.BaseActivityRequestListener;
import com.medic.quotesbook.utils.GAK;
import com.medic.quotesbook.views.adapters.QuotesAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class QuotesListFragment extends Fragment{

    private final String TAG = "QuotesListFragment";

    private static final String ARG_FROM_QUOTESBOOK = "QuotesFragment.FROM_QUOTESBOOK";


    // TODO: Rename and change types of parameters
    private boolean fromQuotesbook;

    private OnFragmentInteractionListener mListener;

    View loaderLayout;
    View quotesView;
    View exceptionLayout;

    private Button reloadButton;

    /**
     * The fragment's ListView/GridView.
     */
    private RecyclerView recyclerView;
    QuotesAdapter adapter;


    // TODO: Rename and change types of parameters
    public static QuotesListFragment newInstance(boolean fromQuotesbook) {
        QuotesListFragment fragment = new QuotesListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_FROM_QUOTESBOOK, fromQuotesbook);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QuotesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            fromQuotesbook = getArguments().getBoolean(ARG_FROM_QUOTESBOOK);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_somequotes, container, false);

        loaderLayout = view.findViewById(R.id.loader_layout);
        quotesView = view.findViewById(R.id.quotes_list);
        exceptionLayout = view.findViewById(R.id.exception_layout);
        reloadButton = (Button) view.findViewById(R.id.reload_button);

        adapter = new QuotesAdapter(null, (BaseActivityRequestListener) getActivity());

        setupRecyclerView(view, adapter);

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuotes();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle();

        getQuotes();
    }

    private void getQuotes(){

        GetQuotesTask task = null;

        if (!fromQuotesbook){
            if (adapter.quotes == null)
                task = new GetSomeQuotesTask(adapter, loaderLayout, quotesView, exceptionLayout);
        }else{

            if (adapter != null && adapter.quotes != null)
                adapter.quotes.clear();

            task = new GetQuotesbookTask(adapter, loaderLayout, quotesView, exceptionLayout, getActivity());
        }

        if (task != null)
            task.execute();
    }

    private void setupRecyclerView(View view, QuotesAdapter adapter){
        recyclerView = (RecyclerView) view.findViewById(R.id.quotes_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (!fromQuotesbook)
            setInfinityScroll(recyclerView, layoutManager);

    }

    void setTitle(){

        ActionBarActivity activity = (ActionBarActivity) getActivity();

        if (fromQuotesbook){
            activity.getSupportActionBar().setTitle(R.string.tl_quotesbook);
        }else{
            activity.getSupportActionBar().setTitle(R.string.tl_home);
        }
    }

    void setInfinityScroll(RecyclerView recycler, final LinearLayoutManager layoutManager){

        final Tracker tracker = ( (AppController) this.getActivity().getApplication()).getDefaultTracker();

        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {

            GetSomeQuotesTask nextTask = null;

            final int PAGE_SIZE = GetSomeQuotesTask.PAGE_SIZE;
            final int MINIMUN_FOR_REQUEST = 6;

            int nextPageCount = PAGE_SIZE;
            int totalItemsRequested = PAGE_SIZE;

            HitBuilders.EventBuilder event = new HitBuilders.EventBuilder(GAK.CATEGORY_SOMEQUOTES, GAK.ACTION_PAGE_PASSED);

            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();


                int remainingItems = totalItemCount - pastVisiblesItems;
                int viewedItems = pastVisiblesItems + visibleItemCount;

                //Log.d(TAG, "Quedan " + Integer.toString(remainingItems));

                if (nextTask != null && nextTask.failedRequest()){
                    totalItemsRequested = totalItemsRequested - PAGE_SIZE;
                }

                if (remainingItems <= MINIMUN_FOR_REQUEST && totalItemsRequested <= totalItemCount) {

                    if (nextTask == null || !nextTask.isLoading()) {

                        //Log.d(TAG, "Se crea una nueva tarea y se ejecuta. remaining: " + Integer.toString(remainingItems));

                        nextTask = new GetSomeQuotesTask((QuotesAdapter) view.getAdapter());
                        nextTask.execute();

                        totalItemsRequested = totalItemsRequested + PAGE_SIZE;

                    }

                }

                if (viewedItems >= nextPageCount){

                    event.setValue(viewedItems);

                    tracker.send(event.build());

                    nextPageCount = nextPageCount + PAGE_SIZE;

                }

                //Log.d(TAG, "Visibles: " + Integer.toString(visibleItemCount) + ", totals: " + Integer.toString(totalItemCount) + ", past: " + Integer.toString(pastVisiblesItems) + ", remaining: " + Integer.toString(remainingItems));
            }
        });
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }*/

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    /*public void setEmptyText(CharSequence emptyText) {
        View emptyView = recyclerView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
