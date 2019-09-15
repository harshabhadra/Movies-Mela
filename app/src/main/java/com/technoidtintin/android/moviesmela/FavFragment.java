package com.technoidtintin.android.moviesmela;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technoidtintin.android.moviesmela.Adapters.FavAdapter;
import com.technoidtintin.android.moviesmela.Model.FavlistItem;
import com.technoidtintin.android.moviesmela.ViewModels.FavViewModel;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment implements FavAdapter.onFavItemClickListener {


    StatefulRecyclerView favRecyclerView;
    FavAdapter favAdapter;
    FavViewModel favViewModel;
    OnFavItemClickListener listener;

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        favRecyclerView = view.findViewById(R.id.favorite_recyclerView);
        favRecyclerView.setHasFixedSize(true);

        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favAdapter = new FavAdapter(getContext(), this);
        favRecyclerView.setAdapter(favAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    FavlistItem favlistItem = favAdapter.getFavMovie(viewHolder.getAdapterPosition());
                    favAdapter.removeItem(viewHolder.getAdapterPosition());
                    favViewModel.deleteMovie(favlistItem);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftLabel("DELETE")
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                        .setSwipeLeftLabelColor(Color.WHITE)
                        .addSwipeLeftBackgroundColor(Color.RED)
                        .addSwipeRightBackgroundColor(Color.RED)
                        .addSwipeRightLabel("DELETE")
                        .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                        .setSwipeRightLabelColor(Color.WHITE)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(favRecyclerView);

        favViewModel = ViewModelProviders.of(this).get(FavViewModel.class);

        //Getting list of movies from database and populating the recyclerView
        favViewModel.getFavListLiveData().observe(this, new Observer<List<FavlistItem>>() {
            @Override
            public void onChanged(List<FavlistItem> favlistItems) {

                if (favlistItems != null) {
                    favAdapter.setFavlistItems(favlistItems);
                } else {
                    Toast.makeText(getContext(), "Add Movies to Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnFavItemClickListener) context;
    }

    @Override
    public void onFavItemClick(int position) {

        FavlistItem favlistItem = favAdapter.getFavMovie(position);
        listener.OnFaveItemClick(favlistItem);
    }

    private void swipeRecyclerView() {


    }

    public interface OnFavItemClickListener {
        void OnFaveItemClick(FavlistItem favlistItem);
    }
}
