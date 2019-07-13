package com.example.android.moviesmela.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moviesmela.Adapters.FavAdapter;
import com.example.android.moviesmela.Adapters.MovieAdapter;
import com.example.android.moviesmela.Model.FavlistItem;
import com.example.android.moviesmela.Model.MovieItem;
import com.example.android.moviesmela.R;
import com.example.android.moviesmela.Utility;
import com.example.android.moviesmela.ViewModels.FavViewModel;
import com.example.android.moviesmela.ViewModels.MovieViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickLisetener, FavAdapter.onFavItemClickListener {

    RecyclerView movieRecycler;
    MovieAdapter movieAdapter;
    MovieViewModel movieViewModel;
    ProgressBar loadingIndicator;
    FavViewModel favViewModel;

    RecyclerView favRecycler;
    FavAdapter favAdapter;

    //Store the path
    String path = "popular";

    SharedPreferences preferences;
    String sort_type;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing recyclerView and progress bar
        movieRecycler = findViewById(R.id.recyclerView);
        loadingIndicator = findViewById(R.id.progressBar);

        //Initializing favRecycler
        favRecycler = findViewById(R.id.fav_recycler);
        favAdapter = new FavAdapter(this, this);

        favViewModel = ViewModelProviders.of(this).get(FavViewModel.class);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                FavlistItem favlistItem = favAdapter.getFavMovie(position);
                favViewModel.deleteMovie(favlistItem);
                favAdapter.removeItem(position);
            }
        }).attachToRecyclerView(favRecycler);

        preferences = getSharedPreferences(getResources().getString(R.string.popular_movies), MODE_PRIVATE);
        sort_type = preferences.getString("sort_type", getResources().getString(R.string.popular));

        if (sort_type.equals(getResources().getString(R.string.popular))) {
            getSupportActionBar().setTitle(getResources().getString(R.string.popular_movies));
        } else if (sort_type.equals("top_rated")) {
            path = "top_rated";
            getSupportActionBar().setTitle(getResources().getString(R.string.top_movies));
        } else {
            path = "fav";
            getSupportActionBar().setTitle("Favorite Movies");
        }

        //Set the progress bar visible
        loadingIndicator.setVisibility(View.VISIBLE);

        //Calculate no. of columns in GridLayout
        int noOfColumns = Utility.calculateNoOfColumns(this, 140);

        //setting movieRecycler
        movieRecycler.setHasFixedSize(true);
        movieRecycler.setLayoutManager(new GridLayoutManager(this, noOfColumns));

        //Setting fav Recycler
        favRecycler.setLayoutManager(new GridLayoutManager(this, noOfColumns));
        favRecycler.setAdapter(favAdapter);

        //Initializing MovieViewModel Class
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        //Checking Network state of the device
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()) {
            if (!path.equals("fav")) {
                loadMovies();
            } else {
                loadFavMovies();
            }
        } else {
            loadFavMovies();
        }
    }

    //Load movies added to favorite list
    private void loadFavMovies() {
        FavViewModel favViewModel = ViewModelProviders.of(MainActivity.this).get(FavViewModel.class);
        favViewModel.getFavListLiveData().observe(MainActivity.this, new Observer<List<FavlistItem>>() {
            @Override
            public void onChanged(List<FavlistItem> favlistItems) {
                if (!favlistItems.isEmpty()) {
                    loadingIndicator.setVisibility(View.GONE);
                    movieRecycler.setVisibility(View.GONE);
                    favRecycler.setVisibility(View.VISIBLE);
                    favAdapter.setFavlistItems(favlistItems);
                    Toast.makeText(getApplicationContext(), "Swipe Left Or Right To remove Item",Toast.LENGTH_SHORT).show();
                }else {
                    loadingIndicator.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "No Favorite Movies",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Load movies by path Popular or top rated
    private void loadMovies() {
        //Getting the list of movie items from movieViewModel class
        movieViewModel.getMovieItemsList(path, getResources().getString(R.string.api_key)).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> movieItems) {

                //Check if the list is null
                if (movieItems != null) {

                    //if not hide the progress bar and load movies list
                    loadingIndicator.setVisibility(View.GONE);
                    movieAdapter = new MovieAdapter(MainActivity.this, MainActivity.this, movieItems);
                    movieRecycler.setAdapter(movieAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.sort) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final SharedPreferences.Editor editor = preferences.edit();
            int selected = 0;
            sort_type = preferences.getString("sort_type", getResources().getString(R.string.popular));
            if (sort_type.equals("popular")) {
                selected = 0;
            } else if (sort_type.equals("top_rated")) {
                selected = 1;
            } else if (sort_type.equals("Favorites")) {
                selected = 2;
            }
            builder.setTitle(getResources().getString(R.string.sort_movies));
            builder.setSingleChoiceItems(R.array.sort_types, selected,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                editor.putString("sort_type", "popular");
                            } else if (which == 1) {
                                editor.putString("sort_type", "top_rated");
                            } else if (which == 2) {
                                editor.putString("sort_type", "Favorites");
                            }
                        }
                    });
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.apply();
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("movie", movieAdapter.getMovieitem(position));
        startActivity(intent);

    }

    @Override
    public void onFavItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("fav", favAdapter.getFavMovie(position));
        startActivity(intent);
    }
}
