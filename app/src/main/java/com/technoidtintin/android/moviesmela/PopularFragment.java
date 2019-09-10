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
public class PopularFragment extends Fragment implements MovieAdapter.OnItemClickLisetener {


    private static final String TAG = PopularFragment.class.getSimpleName();
    StatefulRecyclerView popularRecycler;
    MovieAdapter movieAdapter;
    MovieViewModel movieViewModel;
    OnClickListener listener;
    MovieItem movieItem;
    ProgressBar progressBar;
    Button retryButton;
    private boolean isNetworkAvailable;

    public PopularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        popularRecycler = view.findViewById(R.id.popular_recyclerView);
        popularRecycler.setHasFixedSize(true);
        int noOfColumns = Utility.calculateNoOfColumns(getContext(), 140);
        popularRecycler.setLayoutManager(new GridLayoutManager(getContext(), noOfColumns));
        movieAdapter = new MovieAdapter(getContext(), this);
        popularRecycler.setAdapter(movieAdapter);

        progressBar = view.findViewById(R.id.popular_loading_indicator);
        progressBar.setVisibility(View.VISIBLE);

        retryButton = view.findViewById(R.id.popular_retry_button);
        retryButton.setBackgroundColor(Color.GREEN);
        retryButton.setTextColor(Color.WHITE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                isNetworkAvailable = isNetworkAvailable();
                if (isNetworkAvailable) {
                    retryButton.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    loadMovies();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        isNetworkAvailable = isNetworkAvailable();
        if (isNetworkAvailable) {
            loadMovies();
        } else {
            retryButton.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnClickListener) context;
    }

    @Override
    public void onItemClick(int position) {

        movieItem = movieAdapter.getMovieitem(position);
        listener.onClick(movieItem);
        Log.e(TAG, "Name: " + movieItem.getTitle());
    }

    private void loadMovies() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovieItemsList("popular", getString(R.string.api_key)).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> items) {

                if (items != null) {
                    movieAdapter.addAll(items);
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "list is full");
                } else {
                    progressBar.setVisibility(View.GONE);
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

    public interface OnClickListener {
        void onClick(MovieItem movieItem);
    }

}
