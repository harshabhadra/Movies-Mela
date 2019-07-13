package com.example.android.moviesmela.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.moviesmela.Model.FavlistItem;
import com.example.android.moviesmela.Repository;

import java.util.List;

public class FavViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<FavlistItem>>favListLiveData;

    public FavViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        favListLiveData = repository.getFavMoviesList();
    }

    //Get all fav movies list
    public LiveData<List<FavlistItem>>getFavListLiveData(){
        return favListLiveData;
    }

    //Insert a movie into FavDatabase
    public void insertMovie(FavlistItem favlistItem){
        repository.insertFavMovie(favlistItem);
    }

    //Delete a movie from FavDatabase
    public void deleteMovie(FavlistItem favlistItem){
        repository.deleteFavItem(favlistItem);
    }
}
