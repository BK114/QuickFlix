package com.example.android.quickflix.api;

import com.example.android.quickflix.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by brockrice on 8/10/17. Used for Api query to The Movie Database
 */

public interface ApiInterfaceService {

    /*
     * This Service wil manage all of our URL calls. Ensure BASE_URL is set in Retrofit
     * Each method in Interface specifies end of the url with args. End BASE_URL with "/"
     * Example query - http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="API_KEY"
     */

    @GET("discover/movie?sort_by=popularity.desc")
    Call<MovieResponse> getMostPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    }



