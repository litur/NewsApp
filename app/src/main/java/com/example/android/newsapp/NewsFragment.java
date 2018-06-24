package com.example.android.newsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass which implements LoaderCallbacks
 */
public class NewsFragment extends android.support.v4.app.Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<PieceOfNews>> {

    private ListView myListView;
    private TextView myEmptyView;
    private ProgressBar myProgressBar;
    private FloatingActionButton myRefreshButton;

    private PieceOfNewsAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        myProgressBar = rootView.findViewById(R.id.progressBar);
        myEmptyView = rootView.findViewById(R.id.empty_view);
        myListView = rootView.findViewById(R.id.list);
        myRefreshButton = rootView.findViewById(R.id.refresh_button);

        myListView.setDividerHeight(0);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Gets a reference to the LoaderManager, in order to interact with loaders.
        final LoaderManager myLoaderManager = getLoaderManager();
        // Checks if an internet connection is available for the device
        // If yes, we initialize and launch the loader
        if (ConnectionUtils.checkInternetConnection(getActivity())) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this fragment for the LoaderCallbacks parameter (which is valid
            // because this fragment implements the LoaderCallbacks interface).
            myLoaderManager.initLoader(Constants.NEWS_LOADER_ID, null, this).forceLoad();
        }
        //If not, we show a message to the user
        else {
            myProgressBar.setVisibility(View.GONE);
            myEmptyView.setText(R.string.check_connection_msg);
        }
        //Sets a ClickListener on the Refresh Button to restart the Loader and search for new Data
        myRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checks if an internet connection is available for the device
                // If yes, we initialize and launch the loader
                if (ConnectionUtils.checkInternetConnection(getActivity())) {
                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this fragment for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    myLoaderManager.restartLoader(Constants.NEWS_LOADER_ID, null, NewsFragment.this).forceLoad();
                    myProgressBar.setVisibility(View.VISIBLE);
                    myEmptyView.setText("");
                    myListView.setVisibility(View.INVISIBLE);
                    myRefreshButton.setVisibility(View.INVISIBLE);
                }
                //If not, we show a message to the user
                // TODO we hide the listView to have an homogeneous behaviour of the app, but the best solution for the user would be to keep the data in the adapter even when rotating the device while the same is offline
                //
                else {
                    myProgressBar.setVisibility(View.GONE);
                    myListView.setVisibility(View.INVISIBLE);
                    myRefreshButton.setVisibility(View.VISIBLE);
                    myEmptyView.setText(R.string.check_connection_msg);
                }
            }
        });
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<ArrayList<PieceOfNews>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(getContext(), Constants.GUARDIANAPIURL);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<PieceOfNews>> loader, ArrayList<PieceOfNews> myNews) {
        myProgressBar.setVisibility(View.GONE);
        myRefreshButton.setVisibility(View.VISIBLE);

        // Checks whether we've received an empty or void ArrayList.
        // In this case, we show the "No data found" TextView instead of the listView
        if (myNews == null || myNews.isEmpty()) {
            myEmptyView.setVisibility(View.VISIBLE);
            myEmptyView.setText(R.string.noDataMsg);

        } else {
            adapter = new PieceOfNewsAdapter(getActivity(), myNews);
            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            myListView.setVisibility(View.VISIBLE);
            myListView.setAdapter(adapter);
        }

    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<PieceOfNews>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();

    }


}
