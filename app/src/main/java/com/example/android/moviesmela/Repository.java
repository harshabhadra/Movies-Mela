package com.example.android.moviesmela;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.app.LoaderManager;

import com.example.android.moviesmela.Interfaces.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Repository {

    public static Repository getInstance(){
        return new Repository();
    }

    //Store the list of movie items
    private MutableLiveData<List<MovieItem>>movieItemLiveData = new MutableLiveData<>();

    //Store list of trailers and videos list
    private MutableLiveData<List<MovieItem>>videosLiveData = new MutableLiveData<>();

    //Store list of reviews from the network call
    private MutableLiveData<List<MovieItem>>reviewLiveData = new MutableLiveData<>();

    //Method to get Reviews list
    public LiveData<List<MovieItem>>getReviewsList(String id, String key){
        loadReviewsList(id,key);
        return reviewLiveData;
    }

    //Method to get videos list
    public LiveData<List<MovieItem>>getVieosList(String id, String apikey){
        loadVideosList(id, apikey);
        return videosLiveData;
    }

    //Method to get the list of movie items
    public LiveData<List<MovieItem>> getMovieList(String path, String apiKey){
        loadMovieList(path,apiKey);
        return movieItemLiveData;
    }

    //Network call to get list of reviews
    private void loadReviewsList(String movieId, String apiKey){

        final List<MovieItem>reviewList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.JsonUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<String>call = api.getReviewsList(movieId, apiKey);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!= null){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        for (int i = 0;i<jsonArray.length(); i++){
                            JSONObject reviewObj = jsonArray.getJSONObject(i);
                            String reviewer = reviewObj.getString("author");
                            String review = reviewObj.getString("content");

                            MovieItem movieItem = new MovieItem(reviewer, review);
                            reviewList.add(movieItem);
                            reviewLiveData.setValue(reviewList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.e("Repository","empty response");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //Network call to get videos list
    private void loadVideosList(String movieId, String apiKey){

        final List<MovieItem>videosList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.JsonUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<String>call = api.getVideosList(movieId,apiKey);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!= null){
                    String json = response.body();
                    Log.e("Repository",  "response: " + response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        for ( int i = 0; i<jsonArray.length(); i++){
                            JSONObject videoObj = jsonArray.getJSONObject(i);
                            String videoKey = videoObj.getString("key");
                            String videoName = videoObj.getString("name");

                            MovieItem movieItem = new MovieItem(videoName,videoKey);
                            videosList.add(movieItem);
                            videosLiveData.setValue(videosList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.e("Repository", "empty response");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //Network call to get movie items list
    private void loadMovieList(String path, String key){

        final List<MovieItem>movieItems = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.JsonUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<String>call = api.getString(path, key);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!= null){
                    String json = response.body();
                    try {
                        JSONObject object = new JSONObject(json);
                        JSONArray jsonArray = object.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject movieObj = jsonArray.getJSONObject(i);

                            String id = movieObj.getString("id");
                            int vote = movieObj.getInt("vote_average");

                            String posterImage = movieObj.getString("poster_path");
                            String image_url = "http://image.tmdb.org/t/p/w185/" + posterImage;
                            String title = movieObj.getString("title");
                            String overview = movieObj.getString("overview");
                            String realeseDate = movieObj.getString("release_date");
                            String backdrop = movieObj.getString("backdrop_path");
                            String cover= "http://image.tmdb.org/t/p/w185/" + backdrop;

                            MovieItem movieItem = new MovieItem(id,vote, image_url, title,cover,overview,realeseDate);
                            movieItems.add(movieItem);
                            movieItemLiveData.setValue(movieItems);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
