package com.example.android.moviesmela;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviesmela.Activities.MainActivity;
import com.example.android.moviesmela.Activities.MovieDetailsActivity;
import com.example.android.moviesmela.Adapters.MovieAdapter;
import com.example.android.moviesmela.Model.MovieItem;
import com.example.android.moviesmela.ViewModels.MovieViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment implements MovieAdapter.OnItemClickLisetener {


    StatefulRecyclerView popularRecycler;
    MovieAdapter movieAdapter;
    MovieViewModel movieViewModel;
    private static final String TAG = PopularFragment.class.getSimpleName();
    OnClickListener listener;
    MovieItem movieItem;

    public interface OnClickListener{
        void onClick(MovieItem movieItem);
    }

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_popular, container, false);

        popularRecycler = view.findViewById(R.id.popular_recyclerView);
        popularRecycler.setHasFixedSize(true);
        int noOfColumns = Utility.calculateNoOfColumns(getContext(),140);
        popularRecycler.setLayoutManager(new GridLayoutManager(getContext(),noOfColumns));
        movieAdapter = new MovieAdapter(getContext(),this);
        popularRecycler.setAdapter(movieAdapter);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovieItemsList("popular","30f3bccb9887629e51a30d4a7d4d9dd8").observe(this, new Observer<List<MovieItem>>() {
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
        listener = (OnClickListener)context;
    }

    @Override
    public void onItemClick(int position) {

        movieItem = movieAdapter.getMovieitem(position);
        listener.onClick(movieItem);
        Log.e(TAG,"Name: " + movieItem.getTitle());
    }

}
