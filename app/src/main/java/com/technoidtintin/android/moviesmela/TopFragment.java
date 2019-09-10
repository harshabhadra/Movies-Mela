package com.technoidtintin.android.moviesmela;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.technoidtintin.android.moviesmela.Adapters.MovieAdapter;
import com.technoidtintin.android.moviesmela.Model.MovieItem;
import com.technoidtintin.android.moviesmela.ViewModels.MovieViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment implements MovieAdapter.OnItemClickLisetener {

    private static final String TAG = PopularFragment.class.getSimpleName();
    StatefulRecyclerView topRatedRecycler;
    MovieAdapter movieAdapter;
    MovieViewModel movieViewModel;
    OnTopItemClickListener listener;
    private ProgressBar progressBar;
    private Button retryButton;
    private boolean isNetwork;

    public TopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        topRatedRecycler = view.findViewById(R.id.top_rated_recyclerView);
        topRatedRecycler.setHasFixedSize(true);
        int noOfColumns = Utility.calculateNoOfColumns(getContext(), 140);
        topRatedRecycler.setLayoutManager(new GridLayoutManager(getContext(), noOfColumns));
        movieAdapter = new MovieAdapter(getContext(), this);
        topRatedRecycler.setAdapter(movieAdapter);

        progressBar = view.findViewById(R.id.top_loading_indicator);
        progressBar.setVisibility(View.VISIBLE);

        retryButton = view.findViewById(R.id.top_retry_button);
        retryButton.setBackgroundColor(Color.GREEN);
        retryButton.setTextColor(Color.WHITE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNetwork = isNetworkAvailable();
                if (isNetwork) {
                    loadMovies();
                    retryButton.setVisibility(View.GONE);
                } else {
                    retryButton.setVisibility(View.VISIBLE);
                }
            }
        });

        isNetwork = isNetworkAvailable();
        if (isNetwork) {
            loadMovies();
        } else {
            retryButton.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnTopItemClickListener) context;
    }

    @Override
    public void onItemClick(int position) {

        MovieItem movieItem = movieAdapter.getMovieitem(position);
        listener.onTopItemClick(movieItem);
    }

    private void loadMovies() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovieItemsList("top_rated", getString(R.string.api_key)).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> items) {

                if (items != null) {
                    movieAdapter.addAll(items);
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "list is full");
                } else {
                    Log.e(TAG, "empty list");
                }

            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo != null && networkInfo.isConnected();

    }

    public interface OnTopItemClickListener {
        void onTopItemClick(MovieItem movieItem);
    }
}
