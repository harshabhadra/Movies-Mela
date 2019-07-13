package com.example.android.moviesmela.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.moviesmela.Adapters.VideoAdapter;
import com.example.android.moviesmela.Model.MovieItem;
import com.example.android.moviesmela.R;
import com.example.android.moviesmela.ViewModels.MovieViewModel;

import java.util.List;

public class VideoActivity extends AppCompatActivity implements VideoAdapter.OnItemClickListener {

    RecyclerView videoRecycler;
    VideoAdapter videoAdapter;
    String videoCode;
    ProgressBar loadingIndicator;

    String movieId;

    private String TAG = "VideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        loadingIndicator = findViewById(R.id.video_loading);
        loadingIndicator.setVisibility(View.VISIBLE);

        //Initializing the Video RecyclerView
        videoRecycler = findViewById(R.id.videos);
        videoRecycler.setHasFixedSize(true);
        videoRecycler.setLayoutManager(new LinearLayoutManager(this));

        //Getting the movieId from MovieDetailsActivity
        Intent intent = getIntent();
        if (intent.hasExtra("id")){
            movieId = intent.getStringExtra("id");
            String movieName = intent.getStringExtra("name");
            setTitle(movieName);
        }

        //Initializing the ViewModel class to get reviews list
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getVideosList(movieId,getResources().getString(R.string.api_key)).observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(List<MovieItem> movieItems) {

                if (movieItems!= null){
                    loadingIndicator.setVisibility(View.GONE);
                    Log.e(TAG,"full list");
                    videoAdapter = new VideoAdapter(VideoActivity.this, VideoActivity.this,movieItems);
                    videoRecycler.setAdapter(videoAdapter);

                }else {
                    Log.e(TAG, "empty list");
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {

        //Opening YouTube App when user click on any item via Intent
        videoCode = videoAdapter.getVideoCode(position);
        String videoUrl = "https://www.youtube.com/watch?v=" + videoCode;
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        try {
            VideoActivity.this.startActivity(webIntent);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }
}
