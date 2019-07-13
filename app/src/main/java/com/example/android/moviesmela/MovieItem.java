package com.example.android.moviesmela;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItem implements Parcelable {

    private String id;
    private int voteaverage;
    private String imageUrl, title, backdrop, overview, releaseDate;
    private String dataName, dataContent;

    public MovieItem(String dataName, String dataContent) {
        this.dataName = dataName;
        this.dataContent = dataContent;
    }

    public MovieItem(String id, int voteaverage, String imageUrl, String title, String backdrop, String overview, String releaseDate) {
        this.id = id;
        this.voteaverage = voteaverage;
        this.imageUrl = imageUrl;
        this.title = title;
        this.backdrop = backdrop;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    protected MovieItem(Parcel in) {
        id = in.readString();
        voteaverage = in.readInt();
        imageUrl = in.readString();
        title = in.readString();
        backdrop = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        dataContent = in.readString();
        dataName = in.readString();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public int getVoteaverage() {
        return voteaverage;
    }

    public void setVoteaverage(int voteaverage) {
        this.voteaverage = voteaverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(voteaverage);
        dest.writeString(imageUrl);
        dest.writeString(title);
        dest.writeString(backdrop);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(dataContent);
        dest.writeString(dataName);
    }
}
