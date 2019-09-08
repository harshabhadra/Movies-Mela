package com.example.android.moviesmela;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.moviesmela.Adapters.FavAdapter;
import com.example.android.moviesmela.Model.FavlistItem;
import com.example.android.moviesmela.ViewModels.FavViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment implements FavAdapter.onFavItemClickListener {


    RecyclerView favRecyclerView;
    FavAdapter favAdapter;
    FavViewModel favViewModel;
    OnFavItemClickListener listener;

    public FavFragment() {
        // Required empty public constructor
    }

    public interface OnFavItemClickListener{
        void OnFaveItemClick(FavlistItem favlistItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fav, container, false);

        favRecyclerView = view.findViewById(R.id.favorite_recyclerView);
        favRecyclerView.setHasFixedSize(true);
        int noOfColumns = Utility.calculateNoOfColumns(getContext(),140);
        favRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),noOfColumns));
        favAdapter = new FavAdapter(getContext(),this);
        favRecyclerView.setAdapter(favAdapter);

        favViewModel = ViewModelProviders.of(this).get(FavViewModel.class);
        favViewModel.getFavListLiveData().observe(this, new Observer<List<FavlistItem>>() {
            @Override
            public void onChanged(List<FavlistItem> favlistItems) {

                if (favlistItems != null){
                    favAdapter.setFavlistItems(favlistItems);
                }else {
                    Toast.makeText(getContext(),"Add Movies to Favorite",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnFavItemClickListener)context;
    }

    @Override
    public void onFavItemClick(int position) {

        FavlistItem favlistItem = favAdapter.getFavMovie(position);
        listener.OnFaveItemClick(favlistItem);
    }
}
