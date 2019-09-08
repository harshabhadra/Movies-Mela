package com.example.android.moviesmela;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviesmela.Adapters.MovieAdapter;
import com.example.android.moviesmela.Model.MovieItem;
import com.example.android.moviesmela.ViewModels.MovieViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment implements MovieAdapter.OnItemClickLisetener {

    StatefulRecyclerView topRatedRecycler;
    MovieAdapter movieAdapter;
    MovieViewModel movieViewModel;
    private static final String TAG = PopularFragment.class.getSimpleName();
    OnTopItemClickListener listener;

    public TopFragment() {
        // Required empty public constructor
    }

    public interface OnTopItemClickListener{
        void onTopItemClick(MovieItem movieItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        topRatedRecycler = view.findViewById(R.id.top_rated_recyclerView);
        topRatedRecycler.setHasFixedSize(true);
        int noOfColumns = Utility.calculateNoOfColumns(getContext(),140);
        topRatedRecycler.setLayoutManager(new GridLayoutManager(getContext(),noOfColumns));
        movieAdapter = new MovieAdapter(getContext(),this);
        topRatedRecycler.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovieItemsList("top_rated","30f3bccb9887629e51a30d4a7d4d9dd8").observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> items) {

                if (items != null){
                    movieAdapter.addAll(items);
                    Log.e(TAG,"list is full");
                }else {
                    Log.e(TAG,"empty list");
                }

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnTopItemClickListener)context;
    }

    @Override
    public void onItemClick(int position) {

        MovieItem movieItem = movieAdapter.getMovieitem(position);
        listener.onTopItemClick(movieItem);
    }
}
