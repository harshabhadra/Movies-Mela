package com.technoidtintin.android.moviesmela.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.technoidtintin.android.moviesmela.Model.MovieItem;
import com.technoidtintin.android.moviesmela.Repository;

import java.util.List;

public class MovieViewModel extends ViewModel {

    Repository repository;

    public MovieViewModel() {

        repository = Repository.getInstance();
    }

    //Return list of movies according to choice
    public LiveData<List<MovieItem>> getMovieItemsList(String path, String key) {
        return repository.getMovieList(path, key);
    }

    //Return list of trailers and videos of a particular movie
    public LiveData<List<MovieItem>> getVideosList(String movieId, String key) {
        return repository.getVieosList(movieId, key);
    }

    //Return list of reviews by viewers
    public LiveData<List<MovieItem>> getReviewList(String movieId, String key) {
        return repository.getReviewsList(movieId, key);
    }
}
