package com.example.android.moviesmela.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.moviesmela.Interfaces.FavDao;
import com.example.android.moviesmela.Model.FavlistItem;

@Database(entities = {FavlistItem.class}, version = 2, exportSchema = false)
public abstract class FavDatabase extends RoomDatabase {

    public static FavDatabase INSTANCES;

    public static FavDatabase getInstance(Context context) {
        if (INSTANCES == null) {
            synchronized ((FavDatabase.class)) {
                if (INSTANCES == null) {
                    INSTANCES = Room.databaseBuilder(context.getApplicationContext(),
                            FavDatabase.class, "fav_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCES;
    }

    public abstract FavDao favDao();
}
