package com.technoidtintin.android.moviesmela.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.technoidtintin.android.moviesmela.Model.FavlistItem;

import java.util.List;

@Dao
public interface FavDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavMovie(FavlistItem favlistItem);

    @Query("SELECT * from fav_table ORDER BY movieId Asc")
    LiveData<List<FavlistItem>>getFavMovies();

    @Delete
    void deleteFavMovies(FavlistItem favlistItem);
}
