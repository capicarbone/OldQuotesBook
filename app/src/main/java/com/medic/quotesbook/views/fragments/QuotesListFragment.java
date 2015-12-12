package com.medic.quotesbook.views.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import com.medic.quotesbook.models.Quote;
import com.medic.quotesbook.tasks.GetQuotesTask;
import com.medic.quotesbook.tasks.GetSomeQuotesTask;
import com.medic.quotesbook.tasks.QuotesFromServerTask;
import com.medic.quotesbook.utils.GAK;
import com.medic.quotesbook.views.adapters.QuotesAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import static com.medic.quotesbook.tasks.GetQuotesTask.*;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class QuotesListFragment extends Fragment{

    private final String TAG = "QuotesListFragment";

    private static final String ARG_FROM_SERVER = "QuotesFragment.FROM_SERVER";

    private static final String STATE_QUOTES = "quotes";

    private boolean fromServer;

    View loaderLayout;
    View quotesView;
    View exceptionLayout;

    private Button reloadButton;

    /**
     * The fragment's ListView/GridView.
     */
    private RecyclerView recyclerView;
    QuotesAdapter adapter;

    GetQuotesTask loadTask = null;

    ContextActivity ownerAcitivty;

    GetQuotesTask.QuotesListState listState;

    // TODO: Rename and change types of parameters
    public static QuotesListFragment newInstance(boolean fromSever) {
        QuotesListFragment fragment = new QuotesListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_FROM_SERVER, fromSever);
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

        ownerAcitivty = (ContextActivity) getActivity();

        if (getArguments() != null) {
            fromServer = getArguments().getBoolean(ARG_FROM_SERVER);
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

        Tracker tracker = ((AppController) getActivity().getApplication()).getDefaultTracker();

        listState = new GetQuotesTask.QuotesListState();

        adapter = new QuotesAdapter(null, getActivity(), tracker);


        adapter.withLoader(true); //TODO: With listState validations may be unnecesary


        Quote[] quotes = null;

        if (savedInstanceState != null){
            Parcelable[] parcels = savedInstanceState.getParcelableArray(STATE_QUOTES);

            if (parcels != null){
                quotes = new Quote[parcels.length];

                for (int i = 0; i < parcels.length; i++) {
                    quotes[i] = (Quote) parcels[i];
                }
            }


        }


        setupRecyclerView(view, adapter);

        if (quotes != null && quotes.length > 0){
            adapter.quotes = new ArrayList<Quote>();
            adapter.quotes.addAll(Arrays.asList(quotes));
            //adapter.notifyItemRangeInserted(0, quotes.length);
            adapter.notifyDataSetChanged();
        }

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuotes();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (adapter.quotes != null){

            Quote[] quotes = new Quote[adapter.quotes.size()];

            for (int i = 0; i < adapter.quotes.size() ; i++) {
                quotes[i] = adapter.quotes.get(i);
            }

            //Parcelable[] quotesPacelable = (Parcelable[]) quotes;
            outState.putParcelableArray(STATE_QUOTES, quotes);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle();

        getQuotes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (loadTask != null)
            loadTask.cancel(true);
    }

    private void getQuotes(){

        loadTask = ownerAcitivty.getQuotesProviderTask();
        loadTask.setListAdapter(adapter);
        loadTask.setLoaderLayout(loaderLayout);
        loadTask.setMainLayout(quotesView);
        loadTask.setExceptionLayout(exceptionLayout);
        loadTask.setCtx(getActivity());
        loadTask.setListState(listState);


        if (loadTask.getSourceType() == SOURCETYPE_SERVER){

            if (adapter.quotes == null || adapter.quotes.size() == 0 ) // No nos estamos recuperado de un estado anterior
                loadTask.execute();
            else
                loadTask.showQuotesList();
        }else{

            loadTask.execute();
        }


    }

    private void setupRecyclerView(View view, QuotesAdapter adapter){
        recyclerView = (RecyclerView) view.findViewById(R.id.quotes_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        setInfinityScroll(recyclerView, layoutManager);

    }

    private void setTitle(){

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        if (fromServer){
            activity.getSupportActionBar().setTitle(R.string.tl_quotesbook);
        }else{
            activity.getSupportActionBar().setTitle(R.string.tl_home);
        }
    }

    void setInfinityScroll(final RecyclerView recycler, final LinearLayoutManager layoutManager){

        final Tracker tracker = ( (AppController) this.getActivity().getApplication()).getDefaultTracker();

        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {

            QuotesFromServerTask nextTask = null;

            final int PAGE_SIZE = GetSomeQuotesTask.DEFAULT_PAGE_SIZE;
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

                if (nextTask != null && nextTask.failedRequest()) {
                    listState.itemsRequested = (int) (listState.totalItemsWaited - PAGE_SIZE);
                }

                if (remainingItems <= MINIMUN_FOR_REQUEST && listState.itemsRequested <= totalItemCount) {

                    if (nextTask == null || !nextTask.isLoading()) {

                        //Log.d(TAG, "Se crea una nueva tarea y se ejecuta. remaining: " + Integer.toString(remainingItems));

                        nextTask = (QuotesFromServerTask) ownerAcitivty.getQuotesProviderTask();
                        nextTask.setListAdapter((QuotesAdapter) view.getAdapter());
                        nextTask.setListState(listState);
                        nextTask.execute();

                        listState.itemsRequested = (int) (listState.totalItemsWaited + PAGE_SIZE);

                    }

                }

                if (viewedItems >= nextPageCount) {

                    event.setValue(viewedItems);

                    tracker.send(event.build());

                    nextPageCount = nextPageCount + PAGE_SIZE;

                }

                if (!listState.isNextPage())
                    recycler.setOnScrollListener(null);

                //Log.d(TAG, "Visibles: " + Integer.toString(visibleItemCount) + ", totals: " + Integer.toString(totalItemCount) + ", past: " + Integer.toString(pastVisiblesItems) + ", remaining: " + Integer.toString(remainingItems));
            }
        });
    }

    public boolean isQuotesBook(){
        return fromServer;
    }

    // Listener

    public interface ContextActivity {

        public GetQuotesTask getQuotesProviderTask();
    }

}
