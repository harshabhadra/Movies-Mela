package com.technoidtintin.android.moviesmela;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Adapters.ReviewAdapter;
import com.technoidtintin.android.moviesmela.Model.FavlistItem;
import com.technoidtintin.android.moviesmela.Model.MovieItem;
import com.technoidtintin.android.moviesmela.ViewModels.FavViewModel;
import com.technoidtintin.android.moviesmela.ViewModels.MovieViewModel;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {


    ImageView photoImage;
    TextView movieTitle;
    TextView releaseDate;
    TextView averageRating;
    TextView overview;
    TextView reviewLabel;
    Button videoButton;

    RecyclerView reviewRecycler;
    ReviewAdapter reviewAdapter;
    MovieViewModel viewModel;
    FavViewModel favViewModel;

    String movieId;
    int rating;
    String title, avgRating, summary, date;
    String photo;
    boolean isFav = false;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        reviewLabel = findViewById(R.id.reviewTv);
        movieTitle = findViewById(R.id.movie_title);
        releaseDate = findViewById(R.id.release_date);
        averageRating = findViewById(R.id.rating);
        overview = findViewById(R.id.overview);
        photoImage = findViewById(R.id.photoimage);
        videoButton = findViewById(R.id.video_button);

        //Initializing the Video RecyclerView
        reviewRecycler = findViewById(R.id.review_recycler);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));

        //Initializing the ViewModel class
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        //Initializing fav view model
        favViewModel = ViewModelProviders.of(this).get(FavViewModel.class);

        //Checking the network state
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        //Getting details about movie via parcelable intent
        Intent intent = getIntent();
        if (intent.hasExtra("movie")) {
            MovieItem movieItem = intent.getParcelableExtra("movie");

            if (movieItem != null) {
                movieId = movieItem.getId();


                title = movieItem.getTitle();


                date = movieItem.getReleaseDate();
                summary = movieItem.getOverview();
                rating = movieItem.getVoteaverage();
                photo = movieItem.getImageUrl();
            } else {
                Toast.makeText(getApplicationContext(), "MovieItem is null", Toast.LENGTH_SHORT).show();
            }
            avgRating = rating + "/10";

            Picasso.get().load(photo).into(photoImage);
            movieTitle.setText(title);
            releaseDate.setText(date);
            averageRating.setText(avgRating);
            overview.setText(summary);

        } else if (intent.hasExtra("fav")) {
            isFav = true;
            FavlistItem favlistItem = intent.getParcelableExtra("fav");

            movieId = favlistItem.getMovieId();
            title = favlistItem.getMovieName();
            rating = favlistItem.getAverageRating();
            avgRating = rating + "/10";
            photo = favlistItem.getMoviePosterUrl();
            summary = favlistItem.getMovieDetails();
            date = favlistItem.getReleaseDate();

            movieTitle.setText(title);
            Picasso.get().load(photo).into(photoImage);
            averageRating.setText(avgRating);
            overview.setText(summary);
            releaseDate.setText(date);
        }

        if (networkInfo != null && networkInfo.isConnected()) {
            loadReviews();
            videoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Sending the movieId to VideoActivity via Intent
                    Intent intent = new Intent(MovieDetailsActivity.this, VideoActivity.class);
                    intent.putExtra("id", movieId);
                    intent.putExtra("name", title);
                    startActivity(intent);
                }
            });
        } else {
            reviewLabel.setVisibility(View.GONE);
            videoButton.setText(getResources().getString(R.string.offline_warning));
        }
    }

    private void loadReviews() {
        //Getting the reviews list
        viewModel.getReviewList(movieId, getResources().getString(R.string.api_key)).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> movieItems) {
                if (movieItems != null) {
                    Log.e("MovieDetailsActivity", "Full list");
                    reviewAdapter = new ReviewAdapter(MovieDetailsActivity.this, movieItems);
                    reviewRecycler.setAdapter(reviewAdapter);
                } else {
                    Log.e("MovieDetailsActivity", "empty list");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isFav) {
            getMenuInflater().inflate(R.menu.menu_details, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.menu_fav, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.add) {
            FavlistItem favlistItem = new FavlistItem(rating, movieId, title, photo, summary, date);
            favViewModel.insertMovie(favlistItem);
            Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
            item.setVisible(false);
        } else if (itemId == R.id.remove) {

        }
        return super.onOptionsItemSelected(item);
    }
}
