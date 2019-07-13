package com.example.android.moviesmela.Activities;

import android.content.Intent;
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

import com.example.android.moviesmela.Adapters.ReviewAdapter;
import com.example.android.moviesmela.Adapters.VideoAdapter;
import com.example.android.moviesmela.MovieItem;
import com.example.android.moviesmela.R;
import com.example.android.moviesmela.ViewModels.MovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {


    ImageView photoImage;
    TextView movieTitle;
    TextView releaseDate;
    TextView averageRating;
    TextView overview;
    Button videoButton;

    RecyclerView reviewRecycler;
    ReviewAdapter reviewAdapter;
    MovieViewModel viewModel;

    String movieId;
    int rating;
    String title, avgRating, summary, date;
    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

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

        //Getting details about movie via parcelable intent
        Intent intent = getIntent();
        if (intent.hasExtra("movie")) {
            MovieItem movieItem = intent.getParcelableExtra("movie");

            movieId = movieItem.getId();

            title = movieItem.getTitle();


            date = movieItem.getReleaseDate();
            summary = movieItem.getOverview();
            rating = movieItem.getVoteaverage();
            photo = movieItem.getImageUrl();
            avgRating = rating + "/10";

            Picasso.get().load(photo).into(photoImage);
            movieTitle.setText(title);
            releaseDate.setText(date);
            averageRating.setText(avgRating);
            overview.setText(summary);

        }
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Sending the movieId to VideoActivity via Intent
                Intent intent = new Intent(MovieDetailsActivity.this, VideoActivity.class);
                intent.putExtra("id", movieId);
                startActivity(intent);
            }
        });

        //Getting the reviews list
        viewModel.getReviewList(movieId,getResources().getString(R.string.api_key)).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> movieItems) {
                if (movieItems != null){
                    Log.e("MovieDetailsActivity","Full list");
                    reviewAdapter = new ReviewAdapter(MovieDetailsActivity.this,movieItems);
                    reviewRecycler.setAdapter(reviewAdapter);
                }else {
                    Log.e("MovieDetailsActivity","empty list");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.fav){
            Toast.makeText(getApplicationContext(),"Added to Favorites",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
