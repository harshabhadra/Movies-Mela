package com.technoidtintin.android.moviesmela.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_table")
public class FavlistItem implements Parcelable {


    public static final Creator<FavlistItem> CREATOR = new Creator<FavlistItem>() {
        @Override
        public FavlistItem createFromParcel(Parcel in) {
            return new FavlistItem(in);
        }

        @Override
        public FavlistItem[] newArray(int size) {
            return new FavlistItem[size];
        }
    };
    int averageRating;
    @PrimaryKey
    @NonNull
    private String movieId;
    private String movieName, moviePosterUrl, movieDetails, releaseDate;

    public FavlistItem(int averageRating, String movieId, String movieName, String moviePosterUrl, String movieDetails, String releaseDate) {
        this.averageRating = averageRating;
        this.movieId = movieId;
        this.movieName = movieName;
        this.moviePosterUrl = moviePosterUrl;
        this.movieDetails = movieDetails;
        this.releaseDate = releaseDate;
    }

    protected FavlistItem(Parcel in) {
        averageRating = in.readInt();
        movieId = in.readString();
        movieName = in.readString();
        moviePosterUrl = in.readString();
        movieDetails = in.readString();
        releaseDate = in.readString();
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    public String getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(String movieDetails) {
        this.movieDetails = movieDetails;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(averageRating);
        dest.writeString(movieId);
        dest.writeString(movieName);
        dest.writeString(moviePosterUrl);
        dest.writeString(movieDetails);
        dest.writeString(releaseDate);
    }
}
