package com.technoidtintin.android.moviesmela.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String JsonUrl = "http://api.themoviedb.org/";

    //Get list of videos according to path Popular or Top rated
    @GET("/3/movie/{Path}/")
    Call<String> getString(@Path("Path") String path, @Query("api_key") String apiKey);

    @GET("/3/movie/{Path}/videos")
    Call<String>getVideosList(@Path ("Path")String id,@Query("api_key")String apiKey);

    @GET("/3/movie/{Path}/reviews")
    Call<String>getReviewsList(@Path("Path")String id,@Query("api_key")String apiKey);
}
